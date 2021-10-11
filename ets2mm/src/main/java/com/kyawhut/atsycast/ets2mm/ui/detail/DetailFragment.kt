package com.kyawhut.atsycast.ets2mm.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.ets2mm.R
import com.kyawhut.atsycast.ets2mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.MovieSourceResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoDetailResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.ets2mm.ui.card.CardPresenter
import com.kyawhut.atsycast.ets2mm.ui.source.VideoSourceActivity
import com.kyawhut.atsycast.ets2mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseDetailTvFragment
import com.kyawhut.atsycast.share.model.ActionModel
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.ShareUtils.isDirectPlayExtension
import com.kyawhut.atsycast.share.utils.extension.Extension.getColorValue
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
                "%s (%s)".format(
                    it.videoTitle, it.videoYear
                ),
                it.videoDescription,
                it.videoQuality,
                getColorValue(it.qualityColor)
            )
        }

        if (vm.videoData!!.isMovies) {
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

        vm.getVideoDetail(::onDetailResult)
    }

    private fun onDetailResult(result: NetworkResponse<VideoDetailResponse>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> {
                toggleLoading(true)
            }
            is NetworkStatus.SUCCESS -> {
                toggleLoading(false)
                setGenres(result.data?.videoGenres?.joinToString { it.genreName } ?: "")
                result.data?.videoSeason?.forEachIndexed { index, seasonResponse ->
                    addDetailRow(
                        ListRow(
                            HeaderItem((index + 1).toLong(), seasonResponse.seasonName),
                            ArrayObjectAdapter(
                                CardPresenter(
                                    requireContext(),
                                    result.data?.videoCover ?: ""
                                )
                            ).apply {
                                setItems(seasonResponse.seasonEpisode, EpisodeResponse.diff)
                            }
                        )
                    )
                }
                addDetailRow(
                    ListRow(
                        HeaderItem(
                            (numberOfRowCount + 1).toLong(),
                            "Related %s".format(
                                if (vm.videoData?.isMovies == true) "Movies" else "Series"
                            )
                        ),
                        ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                            setItems(result.data?.videoRelated, VideoResponse.diff)
                        }
                    )
                )
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
                        if (vm.videoDetailResponse == null) return
                        startActivity<VideoSourceActivity>(
                            Constants.EXTRA_APP_NAME to vm.appName,
                            Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                            Constants.EXTRA_VIDEO_DATA to vm.videoDetailResponse,
                            Constants.EXTRA_VIDEO_TITLE to "%s (%s)".format(
                                vm.videoDetailResponse!!.videoTitle,
                                vm.videoData!!.videoYear
                            ),
                            Constants.EXTRA_VIDEO_SOURCE to checkSource(vm.videoDetailResponse?.videoSource).map {
                                VideoSourceModel(
                                    vm.videoDetailResponse!!.videoID.toString(),
                                    it.videoLabel,
                                    url = it.videoURL
                                )
                            }
                        )
                    }
                    2L -> {
                        vm.toggleWatchLater()
                        replaceAction(
                            if (vm.videoData!!.isMovies) 1 else 0
                        ) {
                            id = 2L
                            actionName = if (vm.isWatchLater) "Remove from watch later"
                            else "Add to watch later"
                            icon = if (vm.isWatchLater) R.drawable.ic_remove_watch_later
                            else R.drawable.ic_add_to_watch_later
                        }
                    }
                    3L -> showFullScreenDescription()
                }
            }
        }
    }

    override fun onClickedItem(rowIndex: Int, item: Any) {
        when (item) {
            is VideoResponse -> {
                startActivity<DetailActivity>(
                    Constants.EXTRA_VIDEO_DATA to item,
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                )
            }
            is EpisodeResponse -> {
                if (vm.videoDetailResponse == null || item.episodeURL.isEmpty() || !item.episodeFileType.isDirectPlayExtension) return
                startActivity<VideoSourceActivity>(
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
                    Constants.EXTRA_VIDEO_DATA to vm.videoDetailResponse,
                    Constants.EXTRA_VIDEO_TITLE to "%s - %s".format(
                        vm.videoDetailResponse!!.videoTitle,
                        item.episodeName
                    ),
                    Constants.EXTRA_VIDEO_SOURCE to listOf(
                        VideoSourceModel(
                            item.episodeID.toString(),
                            item.episodeName,
                            url = item.episodeURL
                        )
                    ),
                    Constants.EXTRA_RELATED_EPISODE to mutableListOf<EpisodeResponse>().apply {
                        getDetailRow(rowIndex)?.let { adapter ->
                            (0 until adapter.size()).forEach { index ->
                                with(adapter[index] as EpisodeResponse) {
                                    if (this.episodeFileType.isDirectPlayExtension) {
                                        add(this)
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }

    override fun onItemFocus(item: Any) {
    }

    override fun onResume() {
        replaceAction(
            if (vm.videoData!!.isMovies) 1 else 0
        ) {
            id = 2L
            actionName = if (vm.isWatchLater) "Remove from watch later"
            else "Add to watch later"
            icon = if (vm.isWatchLater) R.drawable.ic_remove_watch_later
            else R.drawable.ic_add_to_watch_later
        }
        super.onResume()
    }

    private fun checkSource(list: List<MovieSourceResponse>?): List<MovieSourceResponse> {
        return list?.filter {
            it.videoFileType.isDirectPlayExtension && it.videoURL.isNotEmpty()
        } ?: listOf()
    }
}
