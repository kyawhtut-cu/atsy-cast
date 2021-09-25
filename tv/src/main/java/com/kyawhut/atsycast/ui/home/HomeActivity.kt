package com.kyawhut.atsycast.ui.home

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.commit
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.databinding.ActivityHomeBinding
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.utils.ToggleBackground
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.ui.dialog.DialogExit.Companion.showExitDialog
import com.kyawhut.atsycast.ui.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@AndroidEntryPoint
class HomeActivity : BaseTvActivity<ActivityHomeBinding>() {

    private val toggleBackground: ToggleBackground by lazy {
        ToggleBackground()
    }

    override val layoutID: Int
        get() = R.layout.activity_home

    private val clockCountDown: CountDownTimer by lazy {
        object : CountDownTimer(1000 * 10, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                executeClock()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.frame_features, HomeFeaturesFragment())
            }
        }

        vb.isFocusSetting = true

        toggleBackground.callBack = {
            vb.backgroundPoster = it
        }

        vb.homeState.onClickListener = {
            (supportFragmentManager.findFragmentById(R.id.frame_features) as HomeFeaturesFragment).refreshPage();
        }

        vb.btnActionSetting.apply {
            setOnFocusChangeListener { _, hasFocus ->
                vb.isFocusSetting = hasFocus
            }
            setOnClickListener {
                startActivity<SettingActivity>()
            }
        }

        executeClock()
    }

    private fun executeClock() {
        vb.clock = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date())
        clockCountDown.start()
    }

    fun toggleLoading(isLoadingState: Boolean) {
        vb.apply {
            isLoading = isLoadingState
            loadingView.toggleAnimation(isLoadingState)
        }
    }

    fun showError(error: String) {
        toggleLoading(false)
        vb.homeState.apply {
            actionText = "ပြန်လည်ကြိုးစားမည်"
            message = error
        }
    }

    override fun onResume() {
        toggleBackground.start()
        super.onResume()
    }

    override fun onPause() {
        toggleBackground.stop()
        super.onPause()
    }

    override fun onDestroy() {
        clockCountDown.cancel()
        toggleBackground.stop()
        super.onDestroy()
    }

    override fun onBackPressed() {
        showExitDialog()
    }
}
