package com.kyawhut.atsycast.gs_mm.ui.search

import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.gs_mm.data.network.response.SearchResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.gs_mm.ui.card.CardPresenter
import com.kyawhut.atsycast.gs_mm.ui.detail.DetailActivity
import com.kyawhut.atsycast.gs_mm.ui.source.VideoSourceActivity
import com.kyawhut.atsycast.gs_mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseSearchSupportFragment
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.network.utils.NetworkError.Companion.printStackTrace
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.ShareUtils
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/7/21
 */
@AndroidEntryPoint
internal class SearchFragment : BaseSearchSupportFragment<SearchViewModel>() {

    override fun onClickRetry(query: String) {
        vm.search(query, ::onSearchResult)
    }

    override val vm: SearchViewModel by viewModels()

    override fun onItemClicked(index: Int, it: Any) {
        if (it is VideoResponse) {
            if (it.videoContentType == "clips") {
                startActivity<VideoSourceActivity>(
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                    Constants.EXTRA_API_KEY to vm.route,
                    Constants.EXTRA_VIDEO_DATA to it,
                    Constants.EXTRA_VIDEO_TITLE to it.videoTitle,
                    Constants.EXTRA_VIDEO_SOURCE to listOf(
                        VideoSourceModel(
                            it.videoID,
                            it.videoTitle,
                            null,
                            it.videoURL,
                            sourceType = ShareUtils.PLAYER_M3U8_EXTENSION,
                            subTitle = it.videoSubTitle
                        )
                    )
                )
            } else startActivity<DetailActivity>(
                Constants.EXTRA_API_KEY to vm.route,
                Constants.EXTRA_VIDEO_DATA to it,
                Constants.EXTRA_APP_NAME to vm.appName,
                Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
            )
        }
    }

    override fun onSearch(query: String) {
        vm.search(query, ::onSearchResult)
    }

    private fun onSearchResult(result: NetworkResponse<List<SearchResponse.Data>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                _isResultFound = result.data!!.isNotEmpty()
                rowAdapter.clear()

                result.data?.forEachIndexed { index, data ->
                    val header = HeaderItem(index.toLong(), data.title)
                    val listAdapter = ArrayObjectAdapter(
                        CardPresenter(requireContext())
                    ).apply {
                        setItems(data.videoList, VideoResponse.diff)
                    }
                    rowAdapter.add(ListRow(header, listAdapter))
                }
            }
            is NetworkStatus.ERROR -> {
                hideLoading()
                result.error?.printStackTrace()
                showError(result.error!!, true)
            }
        }
    }
}
