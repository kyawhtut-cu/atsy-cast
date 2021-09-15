package com.kyawhut.atsycast.ets2mm.ui.detail

import com.kyawhut.atsycast.ets2mm.data.network.response.VideoDetailResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal interface DetailRepository {

    fun isWatchLater(moviesID: Int): Boolean

    fun toggleWatchLater(videoData: VideoResponse)

    suspend fun getVideoDetail(
        videoID: Int,
        isMovies: Boolean,
        callback: (NetworkResponse<VideoDetailResponse>) -> Unit
    )
}
