package com.kyawhut.atsycast.zcm

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.zcm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse
import com.kyawhut.atsycast.zcm.ui.detail.DetailActivity
import com.kyawhut.atsycast.zcm.ui.home.HomeActivity
import com.kyawhut.atsycast.zcm.ui.player.PlayerActivity
import com.kyawhut.atsycast.zcm.utils.Constants

/**
 * @author kyawhtut
 * @date 9/10/21
 */
object ZCMApp {

    fun Context.goToZCM(appName: String, channelLogo: String, apiKey: String) {
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
            Constants.EXTRA_API_KEY to apiKey
        )
    }

    fun Fragment.goToZCM(appName: String, channelLogo: String, apiKey: String) {
        requireContext().goToZCM(appName, apiKey, channelLogo)
    }

    fun Fragment.goToZCMDetail(
        appName: String,
        channelLogo: String,
        apiKey: String,
        data: WatchLaterEntity
    ) {
        context.startActivity<DetailActivity>(
            Constants.EXTRA_API_KEY to apiKey,
            Constants.EXTRA_VIDEO_DATA to data.getData<MoviesResponse>(),
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
        )
    }

    fun Fragment.goToZCMPlayer(
        appName: String,
        channelLogo: String,
        apiKey: String,
        data: RecentlyWatchEntity
    ) {
        context.startActivity<PlayerActivity>(
            Constants.EXTRA_VIDEO_ID to data.videoID.toInt(),
            Constants.EXTRA_IS_RESUME to true,
            Constants.EXTRA_VIDEO_TITLE to data.videoTitle,
            Constants.EXTRA_VIDEO_COVER to data.videoCover,
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
            Constants.EXTRA_IS_ADULT to data.isAdult,
            Constants.EXTRA_VIDEO_SOURCE to VideoSourceModel(
                data.videoID,
                data.videoTitle,
                null,
                data.videoURL,
                data.videoAgent,
                data.videoCookies,
                data.videoCustomHeader
            ),
            Constants.EXTRA_RELATED_EPISODE to Gson().fromJson<List<EpisodeResponse>>(
                data.videoRelatedVideo,
                object : TypeToken<List<EpisodeResponse>>() {}.type
            )
        )
    }

}
