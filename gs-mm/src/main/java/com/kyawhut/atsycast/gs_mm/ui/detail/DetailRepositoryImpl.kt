package com.kyawhut.atsycast.gs_mm.ui.detail

import android.content.Context
import com.google.gson.Gson
import com.kyawhut.atsycast.gs_mm.R
import com.kyawhut.atsycast.gs_mm.data.network.GSMMAPI
import com.kyawhut.atsycast.gs_mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.RelatedResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class DetailRepositoryImpl @Inject constructor(
    private val api: GSMMAPI,
    private val watchLater: WatchLaterSource,
    private val crashlytics: Crashlytics,
) : DetailRepository {

    private fun getWatchLater(route: String, videoID: String): WatchLaterEntity? {
        val watchLater = watchLater.get(SourceType.getSourceType(route))
        val index = watchLater.map {
            it.getData<VideoResponse>()
        }.indexOfFirst {
            it.videoID == videoID
        }
        return if (index == -1) null else watchLater[index]
    }

    override fun isWatchLater(route: String, videoID: String): Boolean {
        return getWatchLater(route, videoID) != null
    }

    override fun toggleWatchLater(route: String, videoData: VideoResponse) {
        val watchLaterEntity = getWatchLater(route, videoData.videoID)
        if (watchLaterEntity != null) watchLater.delete(
            watchLaterEntity.id,
            SourceType.getSourceType(route)
        )
        else watchLater.insert {
            this.videoData = Gson().toJson(videoData)
            videoSourceType = SourceType.getSourceType(route)
        }
    }

    override suspend fun getRelatedMovies(
        context: Context,
        route: String,
        videoID: String,
        callback: (NetworkResponse<RelatedResponse.Data>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.getRelatedVideoList(scriptRequest {
                this.route = route
                payload = mutableMapOf(
                    "sub_route" to "videoRelatedList",
                    "video_id" to videoID,
                )
            }).data
        }
        if (response.isSuccess) {
            if (response.data == null) {
                NetworkResponse.error(
                    NetworkError(context.getString(R.string.lbl_notify_to_developer)),
                    callback
                )
                TelegramHelper.sendLog(
                    context,
                    "<strong>Video List</strong> is null. Please check in server script."
                )
            } else NetworkResponse.success(response.data!!, callback)
        } else NetworkResponse.error(response.error, callback)
    }

    override suspend fun getPlaylist(
        context: Context,
        route: String,
        playlistID: String,
        page: Int,
        callback: (NetworkResponse<RelatedResponse.Data>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.getPlaylistList(scriptRequest {
                this.route = route
                payload = mutableMapOf(
                    "sub_route" to "videoPlaylistList",
                    "playlist_id" to playlistID,
                    "page" to page,
                )
            }).data
        }
        if (response.isSuccess) {
            if (response.data == null) {
                NetworkResponse.error(
                    NetworkError(context.getString(R.string.lbl_notify_to_developer)),
                    callback
                )
                TelegramHelper.sendLog(
                    context,
                    "<strong>Video List</strong> is null. Please check in server script."
                )
            } else NetworkResponse.success(response.data!!, callback)
        } else NetworkResponse.error(response.error, callback)
    }

    override suspend fun getSeriesSeason(
        context: Context,
        route: String,
        videoID: String,
        page: Int,
        callback: (NetworkResponse<List<EpisodeResponse.Data>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            api.getEpisodeList(scriptRequest {
                this.route = route
                payload = mutableMapOf(
                    "sub_route" to "videoEpisodeList",
                    "video_id" to videoID,
                    "page" to page,
                )
            }).data
        }
        if (response.isSuccess) {
            if (response.data == null) {
                NetworkResponse.error(
                    NetworkError(context.getString(R.string.lbl_notify_to_developer)),
                    callback
                )
                TelegramHelper.sendLog(
                    context,
                    "<strong>Video List</strong> is null. Please check in server script."
                )
            } else NetworkResponse.success(response.data ?: listOf(), callback)
        } else NetworkResponse.error(response.error, callback)
    }
}
