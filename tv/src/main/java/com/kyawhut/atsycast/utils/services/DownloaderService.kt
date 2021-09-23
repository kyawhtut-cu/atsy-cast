package com.kyawhut.atsycast.utils.services

import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.core.app.JobIntentService
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.kyawhut.atsycast.share.network.utils.UnsafeOkHttpClient
import com.kyawhut.atsycast.share.utils.extension.IntentExtension.createIntent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Request
import okio.buffer
import okio.sink
import timber.log.Timber
import java.io.File
import java.io.IOException

class DownloaderService : JobIntentService() {

    companion object {
        private const val DOWNLOAD_JOB_ID = 2
        const val extraApkURL: String = "extra.ApkURL"
        var callback: ((message: String, progress: Int) -> Unit)? = null

        fun FragmentActivity.startDownload(vararg data: Pair<String, Any>) {
            enqueueWork(
                this,
                DownloaderService::class.java,
                DOWNLOAD_JOB_ID,
                createIntent(this, DownloaderService::class.java, data)
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.d("Downloader service started.")
    }

    override fun onHandleWork(intent: Intent) {
        Timber.d("onHandleWork => %s", Gson().toJson(intent))
        intent.let {
            try {
                val apkURL = it.getStringExtra(extraApkURL) ?: ""
                val apkName = Uri.parse(apkURL).lastPathSegment ?: ""
                val file = File(
                    getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                    apkName
                )
                if (file.exists()) {
                    file.delete()
                }
                downloadApk(
                    apkName,
                    apkURL
                )
            } catch (e: Exception) {
                e.printStackTrace()
                CoroutineScope(Dispatchers.Main).launch {
                    callback?.invoke(e.localizedMessage ?: "", 0)
                }
            }
        }
    }

    @Throws(Exception::class)
    private fun downloadApk(apkName: String, downloadURL: String) {
        val request = Request.Builder().url(downloadURL).build()
        val client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
            .addNetworkInterceptor {
                val originalResponse = it.proceed(it.request())
                originalResponse.newBuilder().body(
                    ProgressResponseBody(
                        originalResponse.body!!,
                        object : ProgressInterface {
                            override fun update(
                                bytesRead: Long,
                                contentLength: Long,
                                done: Boolean
                            ) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    callback?.invoke(
                                        "",
                                        ((100 * bytesRead) / contentLength).toInt()
                                    )
                                }
                            }
                        })
                ).build()
            }.build()

        val response = client.newCall(request).execute()
        if (!response.isSuccessful) throw IOException(String.format("Unexpected code %s", response))
        val downloadFile = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), apkName)

        val sink = downloadFile.sink().buffer()
        sink.writeAll(response.body!!.source())
        sink.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("Downloader service finished.")
    }
}
