package com.kyawhut.astycast.gsmovie.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.astycast.gsmovie.R
import com.kyawhut.astycast.gsmovie.data.network.response.VideoDetailResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoEpisodeResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.astycast.gsmovie.ui.card.CardPresenter
import com.kyawhut.astycast.gsmovie.ui.source.VideoSourceActivity
import com.kyawhut.astycast.gsmovie.utils.Constants
import com.kyawhut.atsycast.share.base.BaseDetailTvFragment
import com.kyawhut.atsycast.share.model.ActionModel
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
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
                "",
                "",
                R.color.colorWhite
            )

            setGenres(it.videoViewCount)
        }

        vm.getVideoDetail(::onVideoDetailResult)
    }

    private fun onVideoDetailResult(result: NetworkResponse<VideoDetailResponse.Data>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> {
                toggleLoading(true)
            }
            is NetworkStatus.SUCCESS -> {
                toggleLoading(false)
                result.data?.let {
                    vb.apply {
                        detailDescription = it.videoDescription
                        executePendingBindings()
                    }
                    vm.videoDetail = it
                    bindAction(it.videoSource.isNotEmpty())
                    if (it.videoEpisodeSource.isNotEmpty()) addDetailRow(
                        ListRow(
                            HeaderItem(
                                0L,
                                "Episodes"
                            ),
                            ArrayObjectAdapter(
                                CardPresenter(
                                    requireContext(),
                                    it.videoCover
                                )
                            ).apply {
                                setItems(it.videoEpisodeSource, VideoEpisodeResponse.diff)
                            }
                        )
                    )
                    addDetailRow(
                        ListRow(
                            HeaderItem(
                                (numberOfRowCount + 1).toLong(),
                                "Related Movies"
                            ),
                            ArrayObjectAdapter(
                                CardPresenter(
                                    requireContext()
                                )
                            ).apply {
                                setItems(it.videoRelated, VideoResponse.Data.diff)
                            }
                        )
                    )
                }
            }
            is NetworkStatus.ERROR -> {
                toggleLoading(false)
            }
        }
    }

    private fun bindAction(isMovies: Boolean) {
        if (isMovies) {
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
    }

    override fun onClickedAction(item: Any) {
        when (item) {
            is ActionModel -> {
                when (item.id) {
                    1L -> {
                        startActivity<VideoSourceActivity>(
                            Constants.EXTRA_APP_NAME to vm.appName,
                            Constants.EXTRA_API_KEY to vm.route,
                            Constants.EXTRA_VIDEO_DATA to vm.videoDetail,
                            Constants.EXTRA_VIDEO_TITLE to (vm.videoData?.videoTitle ?: ""),
                            Constants.EXTRA_VIDEO_SOURCE to (vm.videoDetail?.videoSource?.map {
                                VideoSourceModel(
                                    (vm.videoData?.videoID ?: "0").toInt(),
                                    it.quality,
                                    url = it.videoURL
                                )
                            } ?: listOf())
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
            is VideoResponse.Data -> {
                startActivity<DetailActivity>(
                    Constants.EXTRA_API_KEY to vm.route,
                    Constants.EXTRA_VIDEO_DATA to item,
                    Constants.EXTRA_APP_NAME to vm.appName,
                )
            }
            is VideoEpisodeResponse -> {
                startActivity<VideoSourceActivity>(
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_API_KEY to vm.route,
                    Constants.EXTRA_VIDEO_DATA to vm.videoDetail,
                    Constants.EXTRA_VIDEO_TITLE to "%s - %s".format(
                        vm.videoData?.videoTitle ?: "",
                        item.episodeTitle
                    ),
                    Constants.EXTRA_VIDEO_SOURCE to item.episodeSource.map {
                        VideoSourceModel(
                            item.episodeID,
                            "%sP".format(it.quality),
                            null,
                            url = it.videoURL
                        )
                    },
                    Constants.EXTRA_RELATED_EPISODE to listOf<VideoEpisodeResponse>()
                )
            }
        }
    }

    override fun onItemFocus(item: Any) {
    }

    private fun toggleWatchLater() {
        replaceAction(
            if (vm.videoDetail?.videoSource?.isEmpty() == false) 1 else 0
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
