package com.kyawhut.atsycast.msubpc.ui.football

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
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

        fun newInstance(key: String, appName: String): FootballFragment {
            return FootballFragment().putArg(
                Constants.EXTRA_PAGE_KEY to key,
                Constants.EXTRA_APP_NAME to appName
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

    private fun onFootballResult(state: NetworkResponse<List<FootballResponse.Data>>) {
        when {
            state.isLoading -> showLoading()
            state.isSuccess -> {
                hideLoading()
                rowsAdapter.setItems(state.data, FootballResponse.Data.diff)
                startEntranceTransition()
            }
            state.isError -> {
                hideLoading()
                showError(state.error!!, true)
            }
        }
    }

    private fun onFootballStreamResult(state: NetworkResponse<FootballStreamResponse>) {
        when (state.networkStatus) {
            is NetworkStatus.LOADING -> showLoading()
            is NetworkStatus.SUCCESS -> {
                hideLoading()
                startActivity<VideoSourceActivity>(
                    Constants.EXTRA_APP_NAME to vm.appName,
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
                        state.data?.let {
                            if (it.streamFHD != null) {
                                add(
                                    VideoSourceModel(
                                        vm.football?.id ?: 0,
                                        "Full HD - 1",
                                        url = AesEncryptDecrypt.getDecryptedString(it.streamFHD),
                                    )
                                )
                            }
                            if (it.streamFHD2 != null) {
                                add(
                                    VideoSourceModel(
                                        vm.football?.id ?: 0,
                                        "Full HD - 2",
                                        url = AesEncryptDecrypt.getDecryptedString(it.streamFHD2),
                                    )
                                )
                            }
                            if (it.streamHD != null) {
                                add(
                                    VideoSourceModel(
                                        vm.football?.id ?: 0,
                                        "HD - 1",
                                        url = AesEncryptDecrypt.getDecryptedString(it.streamHD),
                                    )
                                )
                            }
                            if (it.streamHD2 != null) {
                                add(
                                    VideoSourceModel(
                                        vm.football?.id ?: 0,
                                        "HD - 2",
                                        url = AesEncryptDecrypt.getDecryptedString(it.streamHD2),
                                    )
                                )
                            }
                            if (it.streamSD != null) {
                                add(
                                    VideoSourceModel(
                                        vm.football?.id ?: 0,
                                        "SD - 1",
                                        url = AesEncryptDecrypt.getDecryptedString(it.streamSD),
                                    )
                                )
                            }
                            if (it.streamSD2 != null) {
                                add(
                                    VideoSourceModel(
                                        vm.football?.id ?: 0,
                                        "SD - 2",
                                        url = AesEncryptDecrypt.getDecryptedString(it.streamSD2),
                                    )
                                )
                            }
                        }
                    },
                    Constants.EXTRA_RELATED_EPISODE to mutableListOf<EpisodeResponse>().apply {
                        state.data?.let {
                            if (it.streamFHD != null) {
                                add(
                                    EpisodeResponse(
                                        "${vm.football?.id ?: 0}",
                                        "${vm.football?.id ?: 0}",
                                        "Full HD - 1",
                                        vStream = AesEncryptDecrypt.getDecryptedString(it.streamFHD),
                                    )
                                )
                            }
                            if (it.streamFHD2 != null) {
                                add(
                                    EpisodeResponse(
                                        "${vm.football?.id ?: 0}",
                                        "${vm.football?.id ?: 0}",
                                        "Full HD - 2",
                                        vStream = AesEncryptDecrypt.getDecryptedString(it.streamFHD2),
                                    )
                                )
                            }
                            if (it.streamHD != null) {
                                add(
                                    EpisodeResponse(
                                        "${vm.football?.id ?: 0}",
                                        "${vm.football?.id ?: 0}",
                                        "HD - 1",
                                        vStream = AesEncryptDecrypt.getDecryptedString(it.streamHD),
                                    )
                                )
                            }
                            if (it.streamHD2 != null) {
                                add(
                                    EpisodeResponse(
                                        "${vm.football?.id ?: 0}",
                                        "${vm.football?.id ?: 0}",
                                        "HD - 2",
                                        vStream = AesEncryptDecrypt.getDecryptedString(it.streamHD2),
                                    )
                                )
                            }
                            if (it.streamSD != null) {
                                add(
                                    EpisodeResponse(
                                        "${vm.football?.id ?: 0}",
                                        "${vm.football?.id ?: 0}",
                                        "SD - 1",
                                        vStream = AesEncryptDecrypt.getDecryptedString(it.streamSD),
                                    )
                                )
                            }
                            if (it.streamSD2 != null) {
                                add(
                                    EpisodeResponse(
                                        "${vm.football?.id ?: 0}",
                                        "${vm.football?.id ?: 0}",
                                        "SD - 2",
                                        vStream = AesEncryptDecrypt.getDecryptedString(it.streamSD2),
                                    )
                                )
                            }
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
        if (it is FootballResponse.Data) {
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
