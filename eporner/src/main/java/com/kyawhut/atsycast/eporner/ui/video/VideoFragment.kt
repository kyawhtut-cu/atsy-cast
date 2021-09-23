package com.kyawhut.atsycast.eporner.ui.video

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.eporner.data.network.response.EPornerVideoResponse
import com.kyawhut.atsycast.eporner.ui.card.CardPresenter
import com.kyawhut.atsycast.eporner.ui.player.WebPlayerActivity
import com.kyawhut.atsycast.eporner.utils.Constants
import com.kyawhut.atsycast.share.base.BaseGridSupportFragment
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
            pageKey: String
        ): VideoFragment {
            return VideoFragment().putArg(
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_PAGE_KEY to pageKey,
            )
        }
    }

    override val vm: VideoViewModel by viewModels()
    override val numberOfColumns: Int
        get() = 4
    override val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(CardPresenter(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLoadMore()
    }

    private fun onVideoResult(result: NetworkResponse<List<EPornerVideoResponse>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                if (vm.isFirstPage) rowsAdapter.setItems(result.data, EPornerVideoResponse.diff)
                else rowsAdapter.addAll(rowsAdapter.size(), result.data)
            }
            is NetworkStatus.ERROR -> {
                hideLoading()
                showError(result.error, true)
            }
        }
    }

    override fun onItemClicked(it: Any) {
        if (it is EPornerVideoResponse) {
            startActivity<WebPlayerActivity>(
                Constants.EXTRA_PLAYER_URL to it.epornerURL
            )
        }
    }

    override fun onItemFocus(it: Any) {
        if (it is EPornerVideoResponse) {
            changeBackground(it.epornerThumbnail.src)
        }
    }

    override fun onLoadMore() {
        vm.getData(::onVideoResult)
    }

    override fun onClickRetry() {
        onLoadMore()
    }
}
