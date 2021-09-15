package com.kyawhut.atsycast.msubpc.ui.search

import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.ui.card.CardPresenter
import com.kyawhut.atsycast.msubpc.ui.detail.DetailActivity
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseSearchSupportFragment
import com.kyawhut.atsycast.share.network.utils.NetworkError.Companion.printStackTrace
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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

    private fun onSearchResult(result: NetworkResponse<List<Pair<String, List<VideoResponse>>>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                _isResultFound = result.data!!.isNotEmpty()
                rowAdapter.clear()
                result.data?.filter { it.second.isNotEmpty() }
                    ?.forEachIndexed { index, (title, data) ->
                        val header = HeaderItem(index.toLong(), title)
                        val listAdapter = ArrayObjectAdapter(
                            CardPresenter(requireContext())
                        ).apply {
                            setItems(data, VideoResponse.diff)
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
