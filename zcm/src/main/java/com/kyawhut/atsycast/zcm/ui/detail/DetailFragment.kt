package com.kyawhut.atsycast.zcm.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.share.base.BaseDetailTvFragment
import com.kyawhut.atsycast.share.model.ActionModel
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.NetworkStatus
import com.kyawhut.atsycast.share.utils.ShareUtils.isDirectPlayExtension
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.zcm.R
import com.kyawhut.atsycast.zcm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse
import com.kyawhut.atsycast.zcm.data.network.response.MoviesSourceResponse
import com.kyawhut.atsycast.zcm.data.network.response.SeasonResponse
import com.kyawhut.atsycast.zcm.ui.card.CardPresenter
import com.kyawhut.atsycast.zcm.ui.source.VideoSourceActivity
import com.kyawhut.atsycast.zcm.utils.Constants
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
                it.moviesCover ?: it.moviesImage,
                "%s (%s)".format(
                    it.moviesTitle, it.moviesYear
                ),
                it.moviesDescription,
                "",
                R.color.colorWhite
            )

            setGenres(it.moviesGenres.joinToString { it.genresTitle })
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

        if (vm.videoData!!.isMovies) vm.getRelateMovies(::onRelatedMoviesResult)
        else vm.getSeriesSeason(::onSeriesSeasonResult)
    }

    private fun onRelatedMoviesResult(result: NetworkResponse<List<MoviesResponse>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> {
                toggleLoading(true)
            }
            is NetworkStatus.SUCCESS -> {
                toggleLoading(false)
                addDetailRow(
                    ListRow(
                        HeaderItem(
                            (numberOfRowCount + 1).toLong(),
                            "Related Movies"
                        ),
                        ArrayObjectAdapter(CardPresenter(requireContext())).apply {
                            setItems(result.data, MoviesResponse.diff)
                        }
                    )
                )
            }
            is NetworkStatus.ERROR -> {
                toggleLoading(false)
            }
        }
    }

    private fun onSeriesSeasonResult(result: NetworkResponse<List<SeasonResponse>>) {
        when (result.networkStatus) {
            is NetworkStatus.LOADING -> {
                toggleLoading(true)
            }
            is NetworkStatus.SUCCESS -> {
                toggleLoading(false)
                vm.getRelateMovies(::onRelatedMoviesResult)
                result.data?.filter { it.seasonEpisodeList.isNotEmpty() }
                    ?.forEachIndexed { index, seasonResponse ->
                        addDetailRow(
                            ListRow(
                                HeaderItem(
                                    index.toLong(),
                                    seasonResponse.seasonTitle
                                ),
                                ArrayObjectAdapter(
                                    CardPresenter(
                                        requireContext(),
                                        vm.videoData!!.moviesCover ?: vm.videoData!!.moviesImage
                                    )
                                ).apply {
                                    setItems(seasonResponse.seasonEpisodeList, EpisodeResponse.diff)
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

    override fun onClickedAction(item: Any) {
        when (item) {
            is ActionModel -> {
                when (item.id) {
                    1L -> {
                        startActivity<VideoSourceActivity>(
                            Constants.EXTRA_APP_NAME to vm.appName,
                            Constants.EXTRA_VIDEO_DATA to vm.videoData,
                            Constants.EXTRA_VIDEO_TITLE to "%s (%s)".format(
                                vm.videoData!!.moviesTitle,
                                vm.videoData!!.moviesYear
                            ),
                            Constants.EXTRA_VIDEO_SOURCE to checkSource(vm.videoData?.moviesSource).map {
                                VideoSourceModel(
                                    vm.videoData!!.moviesID,
                                    it.sourceTitle,
                                    url = it.sourceURL
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
            is MoviesResponse -> {
                startActivity<DetailActivity>(
                    Constants.EXTRA_API_KEY to vm.apiKey,
                    Constants.EXTRA_VIDEO_DATA to item,
                    Constants.EXTRA_APP_NAME to vm.appName,
                )
            }
            is EpisodeResponse -> {
                startActivity<VideoSourceActivity>(
                    Constants.EXTRA_APP_NAME to vm.appName,
                    Constants.EXTRA_VIDEO_DATA to vm.videoData,
                    Constants.EXTRA_VIDEO_TITLE to "%s - %s".format(
                        vm.videoData!!.moviesTitle,
                        item.episodeTitle
                    ),
                    Constants.EXTRA_VIDEO_SOURCE to checkSource(item.episodeSource).map {
                        VideoSourceModel(
                            it.sourceID,
                            if (it.quality == null) it.sourceTitle else "%sP".format(
                                it.quality
                            ),
                            if (it.size == null) null else "Size - %s".format(it.size),
                            url = it.sourceURL
                        )
                    },
                    Constants.EXTRA_RELATED_EPISODE to mutableListOf<EpisodeResponse>().apply {
                        getDetailRow(rowIndex)?.let { adapter ->
                            (0 until adapter.size()).forEach { index ->
                                with(adapter[index] as EpisodeResponse) {
                                    if (this.episodeSource.any { it.sourceType.isDirectPlayExtension && it.kind == "both" }) {
                                        this.episodeSource.forEachIndexed { index, moviesSourceResponse ->
                                            add(this.copy().apply {
                                                episodeTitle =
                                                    if (this.episodeSource.size == 1) episodeTitle else "%s - Link %s".format(
                                                        episodeTitle, index + 1
                                                    )
                                                episodeSource = listOf(moviesSourceResponse)
                                            })
                                        }
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

    private fun checkSource(list: List<MoviesSourceResponse>?): List<MoviesSourceResponse> {
        return list?.filter {
            it.sourceType.isDirectPlayExtension && it.kind == "both"
        } ?: listOf()
    }
}
