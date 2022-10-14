package com.kyawhut.atsycast.ui.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import com.kyawhtut.atsycast.telegram.ui.home.TelegramScreen
import com.kyawhut.atsycast.BuildConfig
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.data.network.response.UserResponse
import com.kyawhut.atsycast.databinding.ActivitySplashBinding
import com.kyawhut.atsycast.share.base.BaseTvActivityWithVM
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.ui.dialog.PlayerErrorDialog.Companion.showError
import com.kyawhut.atsycast.share.utils.color.RandomColor
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import com.kyawhut.atsycast.share.utils.extension.Extension.isTv
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.ui.home.HomeActivity
import com.kyawhut.atsycast.ui.password.DialogPassword
import com.kyawhut.atsycast.ui.password.DialogPassword.Companion.showPasswordDialog
import com.kyawhut.atsycast.ui.tvconfirm.TvConfirmActivity
import com.kyawhut.atsycast.ui.update.UpdateActivity
import com.kyawhut.atsycast.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/17/21
 */
@AndroidEntryPoint
class SplashActivity : BaseTvActivityWithVM<ActivitySplashBinding, SplashViewModel>(),
    MotionLayout.TransitionListener {

    override val layoutID: Int
        get() = R.layout.activity_splash
    override val vm: SplashViewModel by viewModels()
    private var isAnimationComplete: Boolean = false
    private var networkResponse: NetworkResponse<UserResponse.Data?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isAdultOpen = false

        vb.apply {
            iosLoading.toggleAnimation(true)
            appVersion = BuildConfig.VERSION_NAME
            appVersionColor = RandomColor().randomColor()

            viewSplash.setTransitionListener(this@SplashActivity)
        }

        if (!isTv) {
            finishAndRemoveTask()
            startActivity<TvConfirmActivity>()
            return
        }

        if (!BuildConfig.DEBUG) checkUpdate()
    }

    private fun checkDeviceStatus() {
        networkResponse = null
        vm.checkDevice {
            networkResponse = it
            processIntent()

        }
    }

    private fun checkUpdate() {
        vm.checkUpdateStatus {
            if (it.isError) {
                showError(if (it.error!!.resId == 0) it.error!!.message else getString(it.error!!.resId)) {
                    checkUpdate()
                }
            } else {
                if (it.data == null) checkDeviceStatus()
                else {
                    // todo: show update or maintenance ui
                    finishAndRemoveTask()
                    startActivity<UpdateActivity>(
                        Constants.EXTRA_APP_STATUS to it.data
                    )
                }
            }
        }
    }

    private fun processIntent() {
        if (!isAnimationComplete || networkResponse == null) return
        networkResponse?.let {
            if (it.isSuccess) {
                if (it.data == null) showPasswordDialog(DialogPassword.Companion.PasswordType.DEVICE) { status ->
                    finishAndRemoveTask()
                    if (status) {
                        startActivity<HomeActivity>()
                    }
                } else {
                    finishAndRemoveTask()
                    startActivity<HomeActivity>()
                }
            } else if (it.isError) {
                showError(if (it.error!!.resId == 0) it.error!!.message else getString(it.error!!.resId)) {
                    checkDeviceStatus()
                }
            }
        }
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
        isAnimationComplete = false
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        isAnimationComplete = true
        if (!BuildConfig.DEBUG) processIntent()
        else startActivity<TelegramScreen>()
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }
}
