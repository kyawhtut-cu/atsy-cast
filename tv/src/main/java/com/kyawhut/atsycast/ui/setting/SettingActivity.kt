package com.kyawhut.atsycast.ui.setting

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.databinding.ActivitySettingBinding
import com.kyawhut.atsycast.share.utils.ShareUtils.deviceID
import com.kyawhut.atsycast.share.utils.ShareUtils.toQRCode
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/16/21
 */
@AndroidEntryPoint
class SettingActivity : FragmentActivity() {

    private lateinit var vb: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = DataBindingUtil.setContentView(this, R.layout.activity_setting)

        vb.apply {
            deviceID = this@SettingActivity.deviceID
            ivQr.setImageBitmap(
                this@SettingActivity.deviceID.toQRCode(
                    Color.WHITE,
                    Color.BLACK
                )
            )
        }

        toggleLoading(false)
    }

    fun toggleLoading(isLoading: Boolean, errorMessage: String = "") {
        vb.apply {
            this.errorMessage = errorMessage
            this.isLoading = isLoading
            iosLoading.toggleAnimation(isLoading)
            executePendingBindings()
        }
        Handler().postDelayed({
            vb.errorMessage = ""
        }, 5000)
    }
}
