package com.kyawhut.atsycast.gs_mm.ui.videogrid

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.gs_mm.ui.card.CardPresenter
import com.kyawhut.atsycast.gs_mm.ui.detail.DetailActivity
import com.kyawhut.atsycast.gs_mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseGridSupportFragment
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@AndroidEntryPoint
internal class VideoGridFragment : BaseGridSupportFragment<VideoViewModel>() {

    companion object {
        fun newInstance(
            appName: String,
            channelLogo: String,
            drawerKey: String,
            route: String
        ): VideoGridFragment {
            return VideoGridFragment().putArg(
                Constants.EXTRA_API_KEY to route,
                Constants.EXTRA_PAGE_KEY to drawerKey,
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_CHANNEL_LOGO to channelLogo,
            )
        }
    }

    override val vm: VideoViewModel by viewModels()

    override val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(CardPresenter(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLoadMore()
    }

    private fun onVideoResult(result: NetworkResponse<List<VideoResponse>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                if (vm.isFirstPage) rowsAdapter.setItems(result.data, VideoResponse.diff)
                else rowsAdapter.addAll(rowsAdapter.size(), result.data)
            }
            is NetworkStatus.ERROR -> {
                hideLoading()
                showError(result.error, true)
            }
        }
    }

    override fun onItemClicked(it: Any) {
        if (it is VideoResponse) {
            startActivity<DetailActivity>(
                Constants.EXTRA_APP_NAME to vm.appName,
                Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                Constants.EXTRA_API_KEY to vm.route,
                Constants.EXTRA_VIDEO_DATA to it
            )
        }
    }

    override fun onItemFocus(it: Any) {
        if (it is VideoResponse) {
            changeBackground(it.videoCover)
        }
    }

    override fun onClickRetry() {
        onLoadMore()
    }

    override fun onLoadMore() {
        vm.getVideoList(::onVideoResult)
    }
}
