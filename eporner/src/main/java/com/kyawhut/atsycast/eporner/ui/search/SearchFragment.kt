package com.kyawhut.atsycast.eporner.ui.search

import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.eporner.data.network.response.EPornerVideoResponse
import com.kyawhut.atsycast.eporner.ui.card.CardPresenter
import com.kyawhut.atsycast.eporner.ui.player.WebPlayerActivity
import com.kyawhut.atsycast.eporner.utils.Constants
import com.kyawhut.atsycast.share.base.BaseSearchSupportFragment
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
        if (it is EPornerVideoResponse) {
            startActivity<WebPlayerActivity>(
                Constants.EXTRA_PLAYER_URL to it.epornerURL
            )
        }
    }

    override fun onSearch(query: String) {
        vm.search(query, ::onSearchResult)
    }

    private fun onSearchResult(result: NetworkResponse<List<EPornerVideoResponse>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                _isResultFound = result.data?.isNotEmpty() ?: false
                if (vm.isFirstPage) {
                    rowAdapter.clear()

                    val header = HeaderItem(0L, "Result")
                    val listAdapter = ArrayObjectAdapter(
                        CardPresenter(requireContext())
                    ).apply {
                        setItems(result.data, EPornerVideoResponse.diff)
                    }
                    rowAdapter.add(ListRow(header, listAdapter))
                } else {
                    with(rowAdapter.get(0) as ListRow) {
                        with(this.adapter as ArrayObjectAdapter) {
                            this.addAll(this.size(), result.data)
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

    override fun onClickRetry(query: String) {
        vm.search(query, ::onSearchResult)
    }

    override fun onLoadMore(query: String) {
        vm.search(query, ::onSearchResult)
    }
}
