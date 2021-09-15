package com.kyawhut.atsycast.msubpc.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.msubpc.R
import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msubpc.data.network.response.MovieStreamResponse
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.ui.card.CardPresenter
import com.kyawhut.atsycast.msubpc.ui.source.VideoSourceActivity
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseDetailTvFragment
import com.kyawhut.atsycast.share.model.ActionModel
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.extension.Extension.getColorValue
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/7/21
 */
@AndroidEntryPoint
internal class DetailFragment : BaseDetailTvFragment<DetailViewModel>() {

    override val vm: DetailViewModel by viewModels()

    override val actionUseDimmer: Boolean
        get() = false

    override val appName: String
        get() = vm.appName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (vm.detailData == null) {
            requireActivity().finishAndRemoveTask()
            return
        }

        vm.detailData?.let {
            bindDetail(
                it.videoCoverImage ?: it.videoPosterImage,
                "%s (%s)".format(it.videoTitle, it.videoYear),
                it.videoReview ?: "",
                if (it.isMovies) it.videoQuality ?: "" else "",
                getColorValue(it.qualityColor)
            )

            setGenres(it.videoGenres ?: "")
        }

        if (vm.detailData!!.isMovies)
            addAction {
                id = 1L
                actionName = "Play"
                icon = R.drawable.ic_action_play
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

        if (vm.detailData!!.isMovies) {
            vm.getRelatedMovies(vm.detailData!!.videoGenres ?: "", ::onRelatedMoviesResult)
        } else {
            vm.getSeasonEpisode(::onSeasonEpisodeResult)
        }
    }

    private fun onMovieStreamResult(result: NetworkResponse<MovieStreamResponse>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> toggleLoading(true)
            is NetworkStatus.SUCCESS -> {
                toggleLoading(false)
                startActivity<VideoSourceActivity>(
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_VIDEO_DATA to vm.detailData,
                    Constants.EXTRA_VIDEO_TITLE to "%s (%s)".format(
                        vm.detailData!!.videoTitle,
                        vm.detailData!!.videoYear
                    ),
                    Constants.EXTRA_VIDEO_SOURCE to mutableListOf<VideoSourceModel>().apply {
                        result.data?.let {
                            if (it.stream != null) {
                                add(
                                    VideoSourceModel(
                                        vm.detailData!!.videoId,
                                        "Free Stream",
                                        url = it.stream!!
                                    )
                                )
                            }
                            if (it.vStream != null) {
                                add(
                                    VideoSourceModel(
                                        vm.detailData!!.videoId,
                                        "VIP Stream",
                                        url = it.vStream!!
                                    )
                                )
                            }
                        }
                    }
                )
            }
            is NetworkStatus.ERROR -> {
                toggleLoading(false)
            }
        }
    }

    private fun onRelatedMoviesResult(result: NetworkResponse<List<VideoResponse>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> toggleLoading(true)
            is NetworkStatus.SUCCESS -> {
                addDetailRow(
                    ListRow(
                        HeaderItem(1L, "Related Movies"),
                        ArrayObjectAdapter(
                            CardPresenter(requireContext())
                        ).apply {
                            setItems(result.data, VideoResponse.diff)
                        }
                    )
                )
                toggleLoading(false)
            }
            is NetworkStatus.ERROR -> {
                toggleLoading(false)
            }
        }
    }

    private fun onSeasonEpisodeResult(result: NetworkResponse<Pair<List<VideoResponse>, List<EpisodeResponse>>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> toggleLoading(true)
            is NetworkStatus.SUCCESS -> {
                result.data?.let { (related, episode) ->

                    if (episode.isNotEmpty()) {
                        addDetailRow(
                            ListRow(
                                HeaderItem(
                                    1L, "%sEpisodes".format(
                                        if (vm.detailData!!.videoSeasonNumber != 0) "Season ${vm.detailData!!.videoSeasonNumber} - " else ""
                                    )
                                ),
                                ArrayObjectAdapter(
                                    CardPresenter(
                                        requireContext(),
                                        poster = vm.detailData!!.videoCoverImage
                                            ?: vm.detailData!!.videoPosterImage
                                    )
                                ).apply {
                                    setItems(episode, EpisodeResponse.diff)
                                }
                            )
                        )
                    }

                    if (related.isNotEmpty()) {
                        addDetailRow(
                            ListRow(
                                HeaderItem(2L, "Related Season"),
                                ArrayObjectAdapter(
                                    CardPresenter(requireContext())
                                ).apply {
                                    setItems(related, VideoResponse.diff)
                                }
                            )
                        )
                    }

                }
                toggleLoading(false)
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
                        vm.getMovieStream(::onMovieStreamResult)
                    }
                    2L -> {
                        vm.toggleWatchLater()
                        replaceAction(
                            if (vm.detailData!!.isMovies) 1 else 0
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
                    Constants.EXTRA_VIDEO_ID to item.videoId,
                    Constants.EXTRA_VIDEO_DATA to item,
                )
            }
            is EpisodeResponse -> {
                startActivity<VideoSourceActivity>(
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_VIDEO_DATA to vm.detailData,
                    Constants.EXTRA_VIDEO_TITLE to "%s - %s".format(
                        vm.detailData!!.videoTitle,
                        item.episodeName
                    ),
                    Constants.EXTRA_RELATED_EPISODE to mutableListOf<EpisodeResponse>().apply {
                        getDetailRow(rowIndex)?.let { adapter ->
                            (0 until adapter.size()).forEach { index ->
                                with(adapter[index] as EpisodeResponse) {

                                    if (this.stream != null) {
                                        add(
                                            this.copy().apply {
                                                this.episodeName = "%s (%s)".format(
                                                    this@with.episodeName, "Free Stream"
                                                )
                                                this.vStream = null
                                            }
                                        )
                                    }

                                    if (this.vStream != null) {
                                        add(
                                            this.copy().apply {
                                                this.episodeName = "%s (%s)".format(
                                                    this@with.episodeName, "VIP Stream"
                                                )
                                                this.stream = null
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    },
                    Constants.EXTRA_VIDEO_SOURCE to mutableListOf<VideoSourceModel>().apply {
                        if (item.stream != null) {
                            add(
                                VideoSourceModel(
                                    item.id.toInt(),
                                    "Free Stream",
                                    url = item.stream!!
                                )
                            )
                        }
                        if (item.vStream != null) {
                            add(
                                VideoSourceModel(
                                    item.id.toInt(),
                                    "VIP Stream",
                                    url = item.vStream!!
                                )
                            )
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
            if (vm.detailData!!.isMovies) 1 else 0
        ) {
            id = 2L
            actionName = if (vm.isWatchLater) "Remove from watch later"
            else "Add to watch later"
            icon = if (vm.isWatchLater) R.drawable.ic_remove_watch_later
            else R.drawable.ic_add_to_watch_later
        }
        super.onResume()
    }
}
