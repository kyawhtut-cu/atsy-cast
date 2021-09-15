package com.kyawhut.atsycast.ets2mm.ui.movies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.ets2mm.ui.card.CardPresenter
import com.kyawhut.atsycast.ets2mm.ui.detail.DetailActivity
import com.kyawhut.atsycast.ets2mm.utils.Constants
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
internal class MoviesFragment : BaseGridSupportFragment<MoviesViewModel>() {

    companion object {
        fun newInstance(genresID: String, appName: String): MoviesFragment {
            return MoviesFragment().putArg(
                Constants.EXTRA_PAGE_KEY to genresID,
                Constants.EXTRA_APP_NAME to appName,
            )
        }
    }

    override val vm: MoviesViewModel by viewModels()

    override val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(CardPresenter(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLoadMore()
    }

    private fun onMoviesResult(result: NetworkResponse<List<VideoResponse>>) {
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
        vm.getMovies(::onMoviesResult)
    }
}
