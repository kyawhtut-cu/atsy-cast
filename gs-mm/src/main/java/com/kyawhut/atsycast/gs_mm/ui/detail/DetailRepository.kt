package com.kyawhut.atsycast.gs_mm.ui.detail

import android.content.Context
import com.kyawhut.atsycast.gs_mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.RelatedResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface DetailRepository {

    fun isWatchLater(route: String, videoID: String): Boolean

    fun toggleWatchLater(route: String, videoData: VideoResponse)

    suspend fun getRelatedMovies(
        context: Context,
        route: String,
        videoID: String,
        callback: (NetworkResponse<RelatedResponse.Data>) -> Unit
    )

    suspend fun getPlaylist(
        context: Context,
        route: String,
        playlistID: String,
        page: Int,
        callback: (NetworkResponse<RelatedResponse.Data>) -> Unit
    )

    suspend fun getSeriesSeason(
        context: Context,
        route: String,
        videoID: String,
        page: Int,
        callback: (NetworkResponse<List<EpisodeResponse.Data>>) -> Unit
    )
}
