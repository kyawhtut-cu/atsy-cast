package com.kyawhut.atsycast.ets2mm.ui.search

import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.ets2mm.data.network.response.SearchResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.ets2mm.ui.card.CardPresenter
import com.kyawhut.atsycast.ets2mm.ui.detail.DetailActivity
import com.kyawhut.atsycast.ets2mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseSearchSupportFragment
import com.kyawhut.atsycast.share.network.utils.NetworkError.Companion.printStackTrace
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
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
            startActivity<DetailActivity>(
                Constants.EXTRA_VIDEO_DATA to it,
                Constants.EXTRA_APP_NAME to vm.appName
            )
        }
    }

    override fun onSearch(query: String) {
        vm.search(query, ::onSearchResult)
    }

    private fun onSearchResult(result: NetworkResponse<SearchResponse>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                _isResultFound = true
                rowAdapter.clear()

                result.data?.let {
                    if (it.moviesList.isNotEmpty()) {
                        val header = HeaderItem(0L, "Movies Result")
                        val listAdapter = ArrayObjectAdapter(
                            CardPresenter(requireContext())
                        ).apply {
                            setItems(it.moviesList.filter {
                                !it.videoTitle.isAdult || isAdultOpen
                            }, VideoResponse.diff)
                        }
                        rowAdapter.add(ListRow(header, listAdapter))
                    }
                    if (it.seriesList.isNotEmpty()) {
                        val header = HeaderItem(1L, "Series Result")
                        val listAdapter = ArrayObjectAdapter(
                            CardPresenter(requireContext())
                        ).apply {
                            setItems(it.seriesList.filter {
                                !it.videoTitle.isAdult || isAdultOpen
                            }, VideoResponse.diff)
                        }
                        rowAdapter.add(ListRow(header, listAdapter))
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
}
