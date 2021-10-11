package com.kyawhut.atsycast.msys.ui.genres

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msys.ui.card.CardPresenter
import com.kyawhut.atsycast.msys.ui.detail.DetailActivity
import com.kyawhut.atsycast.msys.utils.Constants
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
        fun newInstance(
            apiKey: String,
            genresID: Int,
            appName: String,
            channelLogo: String
        ): MoviesFragment {
            return MoviesFragment().putArg(
                Constants.EXTRA_API_KEY to apiKey,
                Constants.EXTRA_PAGE_KEY to genresID,
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_CHANNEL_LOGO to channelLogo,
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

    private fun onMoviesResult(result: NetworkResponse<List<MoviesResponse>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                if (vm.isFirstPage) rowsAdapter.setItems(result.data, MoviesResponse.diff)
                else rowsAdapter.addAll(rowsAdapter.size(), result.data)
            }
            is NetworkStatus.ERROR -> {
                hideLoading()
                showError(result.error, true)
            }
        }
    }

    override fun onItemClicked(it: Any) {
        if (it is MoviesResponse) {
            startActivity<DetailActivity>(
                Constants.EXTRA_APP_NAME to vm.appName,
                Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                Constants.EXTRA_API_KEY to vm.apiKey,
                Constants.EXTRA_VIDEO_DATA to it
            )
        }
    }

    override fun onItemFocus(it: Any) {
        if (it is MoviesResponse) {
            changeBackground(it.moviesCover ?: it.moviesImage)
        }
    }

    override fun onClickRetry() {
        onLoadMore()
    }

    override fun onLoadMore() {
        vm.getMovies(::onMoviesResult)
    }
}
