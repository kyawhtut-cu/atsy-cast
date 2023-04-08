package com.kyawhut.atsycast.msubpc.ui.adult

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.msubpc.data.network.response.AdultResponse
import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msubpc.ui.card.CardPresenter
import com.kyawhut.atsycast.msubpc.ui.player.PlayerActivity
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseGridSupportFragment
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/6/21
 */
@AndroidEntryPoint
internal class AdultFragment : BaseGridSupportFragment<AdultViewModel>() {

    companion object {

        fun newInstance(key: String, appName: String, channelLogo: String): AdultFragment {
            return AdultFragment().putArg(
                Constants.EXTRA_PAGE_KEY to key,
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_CHANNEL_LOGO to channelLogo,
            )
        }
    }

    override val vm: AdultViewModel by viewModels()

    override val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(
            CardPresenter(requireContext())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getAdult(::onNetworkState)
    }

    private fun onNetworkState(state: NetworkResponse<List<AdultResponse>>) {
        when {
            state.isLoading -> showLoading()
            state.isSuccess -> {
                hideLoading()
                rowsAdapter.setItems(state.data, AdultResponse.diff)
                startEntranceTransition()
            }

            state.isError -> {
                hideLoading()
                showError(state.error!!, true)
            }
        }
    }

    override fun onItemClicked(it: Any) {
        if (it is AdultResponse) {
            startActivity<PlayerActivity>(
                Constants.EXTRA_VIDEO_ID to it.adultID,
                Constants.EXTRA_IS_RESUME to false,
                Constants.EXTRA_IS_LIVE to false,
                Constants.EXTRA_VIDEO_TITLE to it.adultTitle,
                Constants.EXTRA_VIDEO_COVER to (it.adultImage),
                Constants.EXTRA_APP_NAME to vm.appName,
                Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                Constants.EXTRA_VIDEO_SOURCE to VideoSourceModel(
                    it.adultID,
                    it.adultTitle,
                    url = it.adultStream!!
                ),
                Constants.EXTRA_RELATED_EPISODE to listOf<EpisodeResponse>(),
                Constants.EXTRA_IS_ADULT to true
            )
        }
    }

    override fun onItemFocus(it: Any) {
        if (it is AdultResponse) {
            changeBackground(it.adultImage)
        }
    }

    override fun onClickRetry() {
        vm.getAdult(::onNetworkState)
    }
}
