package com.kyawhut.atsycast.twod.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import com.kyawhut.atsycast.share.base.BaseTvActivityWithVM
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.utils.ToggleBackground
import com.kyawhut.atsycast.twod.R
import com.kyawhut.atsycast.twod.data.network.response.TodayResponse
import com.kyawhut.atsycast.twod.databinding.Activity2dHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/23/21
 */
@AndroidEntryPoint
internal class HomeActivity : BaseTvActivityWithVM<Activity2dHomeBinding, HomeViewModel>() {

    override val layoutID: Int
        get() = R.layout.activity_2d_home

    override val vm: HomeViewModel by viewModels()
    private val toggleBackground: ToggleBackground by lazy {
        ToggleBackground()
    }
    private var isOpenToday: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toggleLoading(true)

        toggleBackground.callBack = {
            vb.backgroundPoster = it
        }
    }

    private fun processToday() {
        if (isOpenToday) vm.getHomeToday(::onTodayResult)
    }

    private fun onTodayResult(result: NetworkResponse<Pair<String, TodayResponse?>>) {
        toggleLoading(false)
        when {
            result.isSuccess -> {
                vb.twoDView.bind(result.data?.first ?: "", result.data?.second)
                isOpenToday = result.data?.second?.twoD?.isLive == true
                processToday()
            }
            result.isError -> {
            }
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        vb.apply {
            this.isLoading = isLoading
            loadingView.toggleAnimation(isLoading)
        }
    }

    override fun onResume() {
        super.onResume()
        toggleBackground.start()
        processToday()
    }

    override fun onPause() {
        super.onPause()
        toggleBackground.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        toggleBackground.stop()
    }
}
