package com.kyawhut.atsycast.msubpc.ui.series

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.ui.card.CardPresenter
import com.kyawhut.atsycast.msubpc.ui.detail.DetailActivity
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseGridSupportFragment
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/6/21
 */
@AndroidEntryPoint
internal class SeriesFragment : BaseGridSupportFragment<SeriesViewModel>() {

    companion object {

        fun newInstance(key: String, appName: String): SeriesFragment {
            return SeriesFragment().putArg(
                Constants.EXTRA_PAGE_KEY to key,
                Constants.EXTRA_APP_NAME to appName,
            )
        }
    }

    override val vm: SeriesViewModel by viewModels()

    override val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(
            CardPresenter(requireContext())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getSeries(::onNetworkState)
    }

    private fun onNetworkState(state: NetworkResponse<List<VideoResponse>>) {
        when {
            state.isLoading -> showLoading()
            state.isSuccess -> {
                hideLoading()
                rowsAdapter.setItems(state.data, VideoResponse.diff)
                startEntranceTransition()
            }
            state.isError -> {
                hideLoading()
                showError(state.error!!, true)
            }
        }
    }

    override fun onItemClicked(it: Any) {
        if (it is VideoResponse) {
            startActivity<DetailActivity>(
                Constants.EXTRA_VIDEO_DATA to it,
                Constants.EXTRA_APP_NAME to vm.appName,
            )
        }
    }

    override fun onItemFocus(it: Any) {
        if (it is VideoResponse) {
            changeBackground(it.videoCoverImage)
        }
    }

    override fun onClickRetry() {
        vm.getSeries(::onNetworkState)
    }
}
