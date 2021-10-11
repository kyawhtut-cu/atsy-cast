package com.kyawhut.atsycast.gs_mm.ui.videolist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.network.response.DrawerDetailResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.gs_mm.ui.card.CardPresenter
import com.kyawhut.atsycast.gs_mm.ui.detail.DetailActivity
import com.kyawhut.atsycast.gs_mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseRowSupportFragment
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 10/8/21
 */
@AndroidEntryPoint
internal class VideoListFragment : BaseRowSupportFragment<VideoViewModel>() {

    companion object {
        fun newInstance(
            appName: String,
            channelLogo: String,
            drawerKey: String,
            route: String
        ): VideoListFragment {
            return VideoListFragment().putArg(
                Constants.EXTRA_API_KEY to route,
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_CHANNEL_LOGO to channelLogo,
                Constants.EXTRA_PAGE_KEY to drawerKey
            )
        }
    }

    override val vm: VideoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getVideoList(::onVideoListResult)

        verticalGridView.setPadding(
            0,
            resources.getDimensionPixelOffset(R.dimen.videoListPaddingTop),
            0,
            resources.getDimensionPixelOffset(R.dimen.margin_xxxlarge),
        )
    }

    private fun onVideoListResult(result: NetworkResponse<List<DrawerDetailResponse.Data>>) {
        when {
            result.isLoading -> showLoading()
            result.isSuccess -> {
                hideLoading()
                result.data?.forEachIndexed { index, data ->
                    setRowItem(ListRow(
                        HeaderItem(index.toLong(), data.title),
                        ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                            setItems(data.videoList, VideoResponse.diff)
                        }
                    ))
                }
            }
            result.isError -> {
                hideLoading()
                showError(result.error, true)
            }
        }
    }

    override fun onItemFocus(it: Any) {
        if (it is VideoResponse) {
            changeBackground(it.videoCover)
        }
    }

    override fun onItemClicked(headerItem: HeaderItem, it: Any) {
        if (it is VideoResponse) {
            startActivity<DetailActivity>(
                Constants.EXTRA_API_KEY to vm.route,
                Constants.EXTRA_APP_NAME to vm.appName,
                Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                Constants.EXTRA_VIDEO_DATA to it
            )
        }
    }

    override fun onClickRetry() {
        vm.getVideoList(::onVideoListResult)
    }
}
