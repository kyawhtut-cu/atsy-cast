package com.kyawhut.atsycast.doujin.ui.video

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.doujin.BuildConfig
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoResponse
import com.kyawhut.atsycast.doujin.ui.card.CardPresenter
import com.kyawhut.atsycast.doujin.ui.player.PlayerActivity
import com.kyawhut.atsycast.doujin.utils.Constants
import com.kyawhut.atsycast.share.base.BaseGridSupportFragment
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@AndroidEntryPoint
internal class VideoFragment : BaseGridSupportFragment<VideoViewModel>() {

    companion object {
        fun newInstance(
            appName: String,
            channelLogo: String,
            pageKey: String
        ): VideoFragment {
            return VideoFragment().putArg(
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_CHANNEL_LOGO to channelLogo,
                Constants.EXTRA_PAGE_KEY to pageKey,
            )
        }
    }

    override val vm: VideoViewModel by viewModels()
    override val isPagingEnable: Boolean
        get() = !vm.isHot
    override val numberOfColumns: Int
        get() = 4
    override val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(CardPresenter(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLoadMore()
    }

    private fun onVideoResult(result: NetworkResponse<List<DoujinVideoResponse>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                if (vm.isFirstPage) rowsAdapter.setItems(result.data, DoujinVideoResponse.diff)
                else rowsAdapter.addAll(rowsAdapter.size(), result.data)
            }
            is NetworkStatus.ERROR -> {
                hideLoading()
                showError(result.error, true)
            }
        }
    }

    private fun onVideoDetailResult(result: NetworkResponse<DoujinVideoDetailResponse>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                startActivity<PlayerActivity>(
                    Constants.EXTRA_VIDEO_DATA to result.data,
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                    Constants.EXTRA_VIDEO_SOURCE to VideoSourceModel(
                        "0",
                        result.data?.doujinTitle ?: "",
                        url = BuildConfig.MEDIA_BASE_URL + result.data?.doujinURL
                    )
                )
            }
            is NetworkStatus.ERROR -> {
                hideLoading()
                showError(result.error, true)
            }
        }
    }

    override fun onItemClicked(it: Any) {
        if (it is DoujinVideoResponse) {
            vm.getVideoDetail(it.doujinID, ::onVideoDetailResult)
        }
    }

    override fun onItemFocus(it: Any) {
        if (it is DoujinVideoResponse) {
            changeBackground(BuildConfig.MEDIA_BASE_URL + it.doujinImage)
        }
    }

    override fun onLoadMore() {
        vm.getData(requireContext(), ::onVideoResult)
    }

    override fun onClickRetry() {
        if (vm.doujinID.isEmpty()) onLoadMore()
        else vm.getVideoDetail(callback = ::onVideoDetailResult)
    }
}
