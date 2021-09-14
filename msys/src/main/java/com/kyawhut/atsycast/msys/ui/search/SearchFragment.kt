package com.kyawhut.atsycast.msys.ui.search

import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msys.ui.card.CardPresenter
import com.kyawhut.atsycast.msys.ui.detail.DetailActivity
import com.kyawhut.atsycast.msys.utils.Constants
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

    override fun onClickRetry(query: String) {
        vm.search(query, ::onSearchResult)
    }

    override val vm: SearchViewModel by viewModels()

    override fun onItemClicked(index: Int, it: Any) {
        if (it is MoviesResponse) {
            startActivity<DetailActivity>(
                Constants.EXTRA_API_KEY to vm.apiKey,
                Constants.EXTRA_VIDEO_DATA to it,
                Constants.EXTRA_APP_NAME to vm.appName
            )
        }
    }

    override fun onSearch(query: String) {
        vm.search(query, ::onSearchResult)
    }

    private fun onSearchResult(result: NetworkResponse<List<MoviesResponse>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                _isResultFound = result.data!!.isNotEmpty()
                rowAdapter.clear()

                val header = HeaderItem(0L, "Result")
                val listAdapter = ArrayObjectAdapter(
                    CardPresenter(requireContext())
                ).apply {
                    setItems(result.data, MoviesResponse.diff)
                }
                rowAdapter.add(ListRow(header, listAdapter))
            }
            is NetworkStatus.ERROR -> {
                hideLoading()
                result.error?.printStackTrace()
                showError(result.error!!, true)
            }
        }
    }
}
