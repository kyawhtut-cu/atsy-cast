package com.kyawhut.atsycast.msubpc.ui.football

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.msubpc.data.network.response.FootballResponse
import com.kyawhut.atsycast.msubpc.data.network.response.FootballStreamResponse
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.ui.card.CardPresenter
import com.kyawhut.atsycast.msubpc.ui.source.VideoSourceActivity
import com.kyawhut.atsycast.msubpc.utils.AesEncryptDecrypt
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseGridSupportFragment
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/6/21
 */
@AndroidEntryPoint
internal class FootballFragment : BaseGridSupportFragment<FootballViewModel>() {

    companion object {

        private const val DEFAULT_POSTER = "https://i.imgur.com/n46dDce.jpg"

        fun newInstance(key: String, appName: String, channelLogo: String): FootballFragment {
            return FootballFragment().putArg(
                Constants.EXTRA_PAGE_KEY to key,
                Constants.EXTRA_APP_NAME to appName,
                Constants.EXTRA_CHANNEL_LOGO to channelLogo,
            )
        }
    }

    override val vm: FootballViewModel by viewModels()

    override val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(
            CardPresenter(requireContext())
        )
    }

    override val numberOfColumns: Int
        get() = 3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.getFootball(::onFootballResult)
    }

    private fun onFootballResult(state: NetworkResponse<List<FootballResponse>>) {
        when {
            state.isLoading -> showLoading()
            state.isSuccess -> {
                hideLoading()
                rowsAdapter.setItems(state.data, FootballResponse.diff)
                startEntranceTransition()
            }

            state.isError -> {
                hideLoading()
                showError(state.error!!, true)
            }
        }
    }

    private fun onFootballStreamResult(state: NetworkResponse<List<FootballStreamResponse>>) {
        when (state.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                startActivity<VideoSourceActivity>(
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                    Constants.EXTRA_IS_LIVE to true,
                    Constants.EXTRA_VIDEO_DATA to VideoResponse(
                        vm.football?.id ?: 0,
                        vm.football?.matchName ?: "",
                        "",
                        DEFAULT_POSTER,
                        DEFAULT_POSTER,
                        vm.football?.league ?: "",
                        "%s %s".format(
                            vm.football?.date ?: "",
                            vm.football?.time ?: "",
                        )
                    ),
                    Constants.EXTRA_VIDEO_TITLE to (vm.football?.matchName ?: ""),
                    Constants.EXTRA_VIDEO_SOURCE to mutableListOf<VideoSourceModel>().apply {
                        state.data?.forEach {
                            add(
                                VideoSourceModel(
                                    it.footballID,
                                    it.quality,
                                    url = AesEncryptDecrypt.getDecryptedString(it.link),
                                    customHeader = mutableListOf<Pair<String, String>>().apply {
                                        if (it.referer != null) add(
                                            "Referer" to AesEncryptDecrypt.getDecryptedString(
                                                it.referer
                                            )
                                        ) else {
                                            add("Referer" to "msub.fortv.channel 4.1")
                                        }
                                        if (it.cusheader != null && it.cusheaderValue != null) add(
                                            AesEncryptDecrypt.getDecryptedString(it.cusheader) to
                                                    AesEncryptDecrypt.getDecryptedString(it.cusheaderValue)
                                        )
                                    }
                                )
                            )
                        }
                    }
                )
            }

            is NetworkStatus.ERROR -> {
                hideLoading()
                showError(state.error!!, true)
            }
        }
    }

    override fun onItemClicked(it: Any) {
        if (it is FootballResponse) {
            vm.getFootballStream(it, ::onFootballStreamResult)
        }
    }

    override fun onItemFocus(it: Any) {
        changeBackground("https://i.imgur.com/n46dDce.jpg")
    }

    override fun onClickRetry() {
        if (rowsAdapter.size() == 0) vm.getFootball(::onFootballResult)
        else vm.getFootballStream(callback = ::onFootballStreamResult)
    }
}
