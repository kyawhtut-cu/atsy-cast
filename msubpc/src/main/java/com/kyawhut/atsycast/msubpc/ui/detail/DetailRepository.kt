package com.kyawhut.atsycast.msubpc.ui.detail

import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msubpc.data.network.response.MovieStreamResponse
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal interface DetailRepository {

    fun isWatchLater(videoID: Int): Boolean

    fun toggleWatchLater(videoData: VideoResponse)

    suspend fun getMovieStream(
        videoID: Int,
        callback: (NetworkResponse<MovieStreamResponse>) -> Unit
    )

    suspend fun getRelatedMovies(
        genres: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    )

    suspend fun getSeasonEpisode(
        seasonID: Int,
        callback: (NetworkResponse<Pair<List<VideoResponse>, List<EpisodeResponse>>>) -> Unit
    )
}
