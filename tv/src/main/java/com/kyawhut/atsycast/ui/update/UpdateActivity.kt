package com.kyawhut.atsycast.ui.update

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.core.content.FileProvider
import androidx.core.view.updateLayoutParams
import com.kyawhut.atsycast.BuildConfig
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.data.network.response.UpdateResponse
import com.kyawhut.atsycast.databinding.ActivityUpdateBinding
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.utils.extension.Extension.convertDpToPixel
import com.kyawhut.atsycast.utils.Constants
import com.kyawhut.atsycast.utils.services.DownloaderService
import com.kyawhut.atsycast.utils.services.DownloaderService.Companion.startDownload
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

/**
 * @author kyawhtut
 * @date 9/20/21
 */
class UpdateActivity : BaseTvActivity<ActivityUpdateBinding>(), View.OnClickListener {

    companion object {
        private const val FILE_PERMISSION_REQUEST = 0x001
    }

    private val appStatus: UpdateResponse by lazy {
        intent?.getSerializableExtra(Constants.EXTRA_APP_STATUS) as UpdateResponse
    }

    override val layoutID: Int
        get() = R.layout.activity_update
    private var parentWidth: Float = 320f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb.apply {
            updateAnimation.setAnimation(if (appStatus.isMaintenance) R.raw.maintenance_animation else R.raw.update_animation)
            updateVersion = if (appStatus.isMaintenance) getString(R.string.lbl_maintenance)
            else appStatus.versionName
            updateMessage = if (appStatus.isMaintenance) appStatus.maintenanceMessage
            else appStatus.updatMessage
            percent = ""
            if (appStatus.isMaintenance) actionText = getString(R.string.lbl_exit)
            onClickListener = this@UpdateActivity
            executePendingBindings()
        }

        if (!appStatus.isMaintenance) showUpdating()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_action -> {
                when (vb.actionText) {
                    getString(R.string.lbl_exit) -> {
                        finishAndRemoveTask()
                    }
                    getString(R.string.lbl_permission) -> {
                        showUpdating()
                    }
                    getString(R.string.lbl_download), getString(R.string.lbl_retry) -> {
                        onDownloadProgress("", 0)
                        startDownload(
                            DownloaderService.extraApkURL to appStatus.downloadURL
                        )
                    }
                    getString(R.string.lbl_install) -> {
                        installApp()
                    }
                }
            }
        }
    }

    private fun onDownloadProgress(message: String, progress: Int) {
        if (message.isNotEmpty()) {
            vb.apply {
                actionText = getString(R.string.lbl_retry)
                executePendingBindings()
            }
            return
        }
        vb.apply {
            actionText = if (progress == 100) getString(R.string.lbl_install)
            else "%s... %s".format(
                getString(R.string.lbl_download),
                progress
            )
            percent = if (progress == 100) "" else "$progress"
            viewPercent.updateLayoutParams {
                width = (progress * convertDpToPixel(parentWidth).toInt()) / 100
            }
        }
    }

    private fun hasStoragePermission() = EasyPermissions.hasPermissions(
        this,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @AfterPermissionGranted(FILE_PERMISSION_REQUEST)
    private fun showUpdating() {
        vb.actionText = getString(
            if (hasStoragePermission()) R.string.lbl_download
            else R.string.lbl_permission
        )
        if (hasStoragePermission()) {
            return
        }
        EasyPermissions.requestPermissions(
            this,
            "Need storage permission to update new version.",
            FILE_PERMISSION_REQUEST,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private fun installApp() {
        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "msys-app.apk")
        val install = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val contentUri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".update_provider",
                file
            )
            Intent(Intent.ACTION_VIEW).apply {
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                data = contentUri
            }
        } else {
            val uri = getFileUri(file.absolutePath)
            Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(
                    uri,
                    "application/vnd.android.package-archive"
                )
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
        }
        startActivity(install)
    }

    private fun getFileUri(filePath: String): Uri {
        return if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(this, "$packageName.update_provider", File(filePath))
        } else Uri.fromFile(File(filePath))
    }

    override fun onStart() {
        super.onStart()
        DownloaderService.callback = ::onDownloadProgress
    }

    override fun onDestroy() {
        super.onDestroy()
        DownloaderService.callback = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onBackPressed() {
        if (!vb.percent.isNullOrEmpty()) return
        super.onBackPressed()
    }
}
