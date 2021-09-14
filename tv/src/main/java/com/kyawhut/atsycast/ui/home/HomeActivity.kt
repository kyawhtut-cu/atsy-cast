package com.kyawhut.atsycast.ui.home

import android.os.Bundle
import androidx.fragment.app.commit
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.databinding.ActivityHomeBinding
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.ui.dialog.PlayerErrorDialog.Companion.showPlayerError
import com.kyawhut.atsycast.share.utils.ToggleBackground
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                add(R.id.frame_features, HomeFeaturesFragment())
            }
        }

        toggleBackground.callBack = {
            vb.backgroundPoster = it
        }

        vb.homeState.onClickListener = {
            (supportFragmentManager.findFragmentById(R.id.frame_features) as HomeFeaturesFragment).refreshPage();
        }
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
        toggleBackground.stop()
        super.onDestroy()
    }
}
