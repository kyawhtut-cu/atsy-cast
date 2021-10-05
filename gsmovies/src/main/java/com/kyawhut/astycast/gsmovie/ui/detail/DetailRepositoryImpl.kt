package com.kyawhut.astycast.gsmovie.ui.detail

import android.content.Context
import com.google.gson.Gson
import com.kyawhut.astycast.gsmovie.R
import com.kyawhut.astycast.gsmovie.data.network.GSAPI
import com.kyawhut.astycast.gsmovie.data.network.response.VideoDetailResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import com.kyawhut.atsycast.share.utils.SourceType
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class DetailRepositoryImpl @Inject constructor(
    private val api: GSAPI,
    private val watchLater: WatchLaterSource,
) : DetailRepository {

    private fun getWatchLater(source: String, moviesID: String): WatchLaterEntity? {
        val watchLater = watchLater.get(SourceType.getSourceType(source))
        val index = watchLater.map {
            Gson().fromJson(it.videoData, VideoResponse.Data::class.java)
        }.indexOfFirst {
            it.videoID == moviesID
        }
        return if (index == -1) null else watchLater[index]
    }

    override fun isWatchLater(source: String, moviesID: String): Boolean {
        return getWatchLater(source, moviesID) != null
    }

    override fun toggleWatchLater(source: String, videoData: VideoResponse.Data) {
        val watchLaterEntity = getWatchLater(source, videoData.videoID)
        if (watchLaterEntity != null) watchLater.delete(
            watchLaterEntity.id,
            SourceType.getSourceType(source)
        )
        else watchLater.insert {
            this.videoData = Gson().toJson(videoData)
            videoSourceType = SourceType.getSourceType(source)
        }
    }

    override suspend fun getVideoDetail(
        context: Context,
        route: String,
        videoID: String,
        callback: (NetworkResponse<VideoDetailResponse.Data>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute {
            api.getVideoDetail(scriptRequest {
                this.route = route
                payload = mutableMapOf(
                    "video_id" to videoID,
                    "sub_route" to "videoDetail"
                )
            }).data
        }
        if (response.isSuccess) {
            if (response.data == null) {
                NetworkResponse.error(
                    NetworkError(context.getString(R.string.lbl_notify_to_developer)),
                    callback
                )
                TelegramHelper.sendLog("<strong>Video Detail</strong> is null. Please check in server script.")
            } else {
                NetworkResponse.success(response.data!!, callback)
            }
        } else NetworkResponse.error(
            response.error,
            callback
        )
    }
}
