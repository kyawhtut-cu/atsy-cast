package com.kyawhut.atsycast.gs_mm.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.model.HeaderModel
import com.kyawhut.atsycast.gs_mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.RelatedResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.gs_mm.ui.card.CardPresenter
import com.kyawhut.atsycast.gs_mm.ui.source.VideoSourceActivity
import com.kyawhut.atsycast.gs_mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseDetailTvFragment
import com.kyawhut.atsycast.share.model.ActionModel
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.ShareUtils
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@AndroidEntryPoint
internal class DetailFragment : BaseDetailTvFragment<DetailViewModel>() {

    override val vm: DetailViewModel by activityViewModels()

    override val actionUseDimmer: Boolean
        get() = false

    override val appName: String
        get() = vm.appName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (vm.videoData == null) {
            requireActivity().finishAndRemoveTask()
            return
        }

        vm.videoData?.let {
            bindDetail(
                it.videoCover,
                it.videoTitle,
                it.videoDescription,
                "",
                R.color.colorWhite
            )

            setGenres(it.videoCompanyName)
        }

        if (vm.videoData!!.videoURL.isNotEmpty()) {
            addAction {
                id = 1L
                actionName = "Play"
                icon = R.drawable.ic_action_play
            }
        }
        addAction {
            id = 2L
            actionName = if (vm.isWatchLater) "Remove from watch later" else "Add to watch later"
            icon = if (vm.isWatchLater) R.drawable.ic_remove_watch_later
            else R.drawable.ic_add_to_watch_later
        }

        addAction {
            id = 3L
            actionName = "Detail"
            icon = R.drawable.ic_action_review
        }

        if (vm.videoData!!.videoURL.isNotEmpty()) {
            if (vm.videoData!!.playlistID.isNotEmpty()) vm.getPlaylistList(::onPlaylistResult)
            else vm.getRelateMovies(::onRelatedMoviesResult)
        } else vm.getSeriesSeason(::onSeriesSeasonResult)
    }

    override val onLoadMoreItem: ((headerItem: Any) -> Unit) = {
        if (it is HeaderModel) {
            if (it.type == "episode") {
                vm.getSeriesSeason(::onSeriesSeasonResult)
            } else if (it.type == "playlist") {
                vm.getPlaylistList(::onRelatedMoviesResult)
            }
        }
    }

    private fun onRelatedMoviesResult(result: NetworkResponse<RelatedResponse.Data>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> {
                toggleLoading(true)
            }
            is NetworkStatus.SUCCESS -> {
                toggleLoading(false)
                if (result.data?.videoList?.isEmpty() == true) return
                addDetailRow(
                    ListRow(
                        HeaderModel(
                            (numberOfRowCount + 1).toLong(),
                            result.data?.title ?: ""
                        ),
                        ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                            setItems(result.data?.videoList, VideoResponse.diff)
                        }
                    )
                )
            }
            is NetworkStatus.ERROR -> {
                toggleLoading(false)
            }
        }
    }

    private fun onPlaylistResult(result: NetworkResponse<RelatedResponse.Data>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> {
                toggleLoading(true)
            }
            is NetworkStatus.SUCCESS -> {
                toggleLoading(false)
                if (result.data?.videoList?.isEmpty() == true) return
                if (vm.isFirstPlaylistPage) {
                    vm.getRelateMovies(::onRelatedMoviesResult)
                    addDetailRow(
                        ListRow(
                            HeaderModel(
                                (numberOfRowCount + 1).toLong(),
                                result.data?.title ?: "",
                                "playlist"
                            ),
                            ArrayObjectAdapter(CardPresenter(requireContext(), true)).apply {
                                setItems(result.data?.videoList, VideoResponse.diff)
                            }
                        )
                    )
                } else {
                    with(getDetailRow {
                        (it as HeaderModel).type == "playlist"
                    }) {
                        this?.addAll(this.size(), result.data?.videoList)
                    }
                }
            }
            is NetworkStatus.ERROR -> {
                toggleLoading(false)
            }
        }
    }

    private fun onSeriesSeasonResult(result: NetworkResponse<List<EpisodeResponse.Data>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> {
                toggleLoading(true)
            }
            is NetworkStatus.SUCCESS -> {
                toggleLoading(false)
                if (result.data?.isEmpty() == true) return
                if (vm.isFirstEpisodePage) {
                    if (vm.videoData!!.playlistID.isNotEmpty()) vm.getPlaylistList(::onPlaylistResult)
                    else vm.getRelateMovies(::onRelatedMoviesResult)
                    vb.apply {
                        detailDescription = result.data?.first()?.episodeDescription ?: ""
                        detailTitle = "%s - %s".format(
                            vm.videoData!!.videoTitle,
                            result.data?.first()?.episodeTitle ?: ""
                        )
                        executePendingBindings()
                    }
                    addDetailRow(
                        ListRow(
                            HeaderModel(
                                1L,
                                "Episode",
                                "episode"
                            ),
                            ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                                setItems(result.data, EpisodeResponse.Data.diff)
                            }
                        )
                    )
                } else {
                    with(getDetailRow {
                        (it as HeaderModel).type == "episode"
                    }) {
                        this?.addAll(this.size(), result.data)
                    }
                }
            }
            is NetworkStatus.ERROR -> {
                toggleLoading(false)
            }
        }
    }

    override fun onClickedAction(item: Any) {
        when (item) {
            is ActionModel -> {
                when (item.id) {
                    1L -> {
                        startActivity<VideoSourceActivity>(
                            Constants.EXTRA_APP_NAME to vm.appName,
                            Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                            Constants.EXTRA_API_KEY to vm.route,
                            Constants.EXTRA_VIDEO_DATA to vm.videoData,
                            Constants.EXTRA_VIDEO_TITLE to vm.videoData!!.videoTitle,
                            Constants.EXTRA_VIDEO_SOURCE to listOf(
                                VideoSourceModel(
                                    vm.videoData!!.videoID,
                                    vm.videoData!!.videoTitle,
                                    null,
                                    vm.videoData!!.videoURL,
                                    sourceType = ShareUtils.PLAYER_M3U8_EXTENSION,
                                    subTitle = vm.videoData!!.videoSubTitle
                                )
                            )
                        )
                    }
                    2L -> {
                        vm.toggleWatchLater()
                        toggleWatchLater()
                    }
                    3L -> showFullScreenDescription()
                }
            }
        }
    }

    override fun onClickedItem(rowIndex: Int, item: Any) {
        when (item) {
            is VideoResponse -> {
                if (item.videoContentType == "clips") {
                    startActivity<VideoSourceActivity>(
                        Constants.EXTRA_APP_NAME to vm.appName,
                        Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                        Constants.EXTRA_API_KEY to vm.route,
                        Constants.EXTRA_VIDEO_DATA to item,
                        Constants.EXTRA_VIDEO_TITLE to item.videoTitle,
                        Constants.EXTRA_VIDEO_SOURCE to listOf(
                            VideoSourceModel(
                                item.videoID,
                                item.videoTitle,
                                null,
                                item.videoURL,
                                sourceType = ShareUtils.PLAYER_M3U8_EXTENSION,
                                subTitle = vm.videoData!!.videoSubTitle
                            )
                        )
                    )
                } else startActivity<DetailActivity>(
                    Constants.EXTRA_API_KEY to vm.route,
                    Constants.EXTRA_VIDEO_DATA to item,
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                )
            }
            is EpisodeResponse.Data -> {
                vm.videoData!!.videoDescription = item.episodeDescription
                startActivity<VideoSourceActivity>(
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                    Constants.EXTRA_VIDEO_DATA to vm.videoData,
                    Constants.EXTRA_API_KEY to vm.route,
                    Constants.EXTRA_VIDEO_TITLE to "%s - %s".format(
                        vm.videoData!!.videoTitle,
                        item.episodeTitle
                    ),
                    Constants.EXTRA_VIDEO_SOURCE to listOf(
                        VideoSourceModel(
                            item.episodeID,
                            item.episodeTitle,
                            null,
                            item.episodeURL,
                            sourceType = ShareUtils.PLAYER_M3U8_EXTENSION,
                            subTitle = item.episodeSubtitle
                        )
                    ),
                    Constants.EXTRA_RELATED_EPISODE to mutableListOf<EpisodeResponse.Data>().apply {
                        getDetailRow(rowIndex)?.let { adapter ->
                            (0 until adapter.size()).forEach { index ->
                                with(adapter[index] as EpisodeResponse.Data) {
                                    add(this)
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    override fun onItemFocus(item: Any) {
        if (item is EpisodeResponse.Data) {
            vb.apply {
                detailDescription = item.episodeDescription
                detailBackground = item.episodeCover
                detailTitle = "%s - %s".format(vm.videoData!!.videoTitle, item.episodeTitle)
                executePendingBindings()
            }
        }
    }

    private fun toggleWatchLater() {
        replaceAction(
            if (vm.videoData!!.videoURL.isNotEmpty()) 1 else 0
        ) {
            id = 2L
            actionName = if (vm.isWatchLater) "Remove from watch later"
            else "Add to watch later"
            icon = if (vm.isWatchLater) R.drawable.ic_remove_watch_later
            else R.drawable.ic_add_to_watch_later
        }
    }

    override fun onResume() {
        toggleWatchLater()
        super.onResume()
    }
}
