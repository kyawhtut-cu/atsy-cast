package com.kyawhut.atsycast.doujin.ui.search

import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.doujin.BuildConfig
import com.kyawhut.atsycast.doujin.data.network.response.DoujinPageVideoResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoResponse
import com.kyawhut.atsycast.doujin.ui.card.CardPresenter
import com.kyawhut.atsycast.doujin.ui.player.PlayerActivity
import com.kyawhut.atsycast.doujin.utils.Constants
import com.kyawhut.atsycast.share.base.BaseSearchSupportFragment
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.network.utils.NetworkError.Companion.printStackTrace
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/7/21
 */
@AndroidEntryPoint
internal class SearchFragment : BaseSearchSupportFragment<SearchViewModel>() {

    override val vm: SearchViewModel by viewModels()
    override val isPagingEnable: Boolean
        get() = true

    override fun onItemClicked(index: Int, it: Any) {
        if (it is DoujinVideoResponse) {
            vm.getVideoDetail(it.doujinID, ::onVideoDetailResult)
        }
    }

    override fun onSearch(query: String) {
        vm.search(query, ::onSearchResult)
    }

    private fun onSearchResult(result: NetworkResponse<DoujinPageVideoResponse>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                _isResultFound = result.data?.data?.isNotEmpty() ?: false
                if (vm.isFirstPage) {
                    rowAdapter.clear()

                    val header = HeaderItem(0L, "Result")
                    val listAdapter = ArrayObjectAdapter(
                        CardPresenter(requireContext())
                    ).apply {
                        setItems(result.data?.data, DoujinVideoResponse.diff)
                    }
                    rowAdapter.add(ListRow(header, listAdapter))
                } else {
                    with(rowAdapter.get(0) as ListRow) {
                        with(this.adapter as ArrayObjectAdapter) {
                            this.addAll(this.size(), result.data?.data)
                        }
                    }
                }
            }
            is NetworkStatus.ERROR -> {
                hideLoading()
                result.error?.printStackTrace()
                showError(result.error!!, true)
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
                    Constants.EXTRA_VIDEO_SOURCE to VideoSourceModel(
                        0,
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

    override fun onClickRetry(query: String) {
        if (vm.doujinID.isEmpty()) vm.search(query, ::onSearchResult)
        else vm.getVideoDetail(callback = ::onVideoDetailResult)
    }

    override fun onLoadMore(query: String) {
        vm.search(query, ::onSearchResult)
    }
}
