package com.kyawhut.astycast.gsmovie.ui.video

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.astycast.gsmovie.ui.card.CardPresenter
import com.kyawhut.astycast.gsmovie.ui.detail.DetailActivity
import com.kyawhut.astycast.gsmovie.utils.Constants
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
internal class VideoFragment : BaseGridSupportFragment<VideoViewModel>() {

    companion object {
        fun newInstance(
            route: String,
            categoryID: Int,
            appName: String,
            channelLogo: String,
        ): VideoFragment {
            return VideoFragment().putArg(
                Constants.EXTRA_API_KEY to route,
                Constants.EXTRA_PAGE_KEY to categoryID,
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

    private fun onVideoResult(result: NetworkResponse<List<VideoResponse.Data>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                if (vm.isFirstPage) rowsAdapter.setItems(result.data, VideoResponse.Data.diff)
                else rowsAdapter.addAll(rowsAdapter.size(), result.data)
            }

            is NetworkStatus.ERROR -> {
                hideLoading()
                showError(result.error, true)
            }
        }
    }

    override fun onItemClicked(it: Any) {
        if (it is VideoResponse.Data) {
            startActivity<DetailActivity>(
                Constants.EXTRA_APP_NAME to vm.appName,
                Constants.EXTRA_API_KEY to vm.route,
                Constants.EXTRA_VIDEO_DATA to it,
                Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
            )
        }
    }

    override fun onItemFocus(it: Any) {
        if (it is VideoResponse.Data) {
            changeBackground(it.videoCover ?: it.videoPoster)
        }
    }

    override fun onClickRetry() {
        onLoadMore()
    }

    override fun onLoadMore() {
        vm.getVideoList(::onVideoResult)
    }
}
