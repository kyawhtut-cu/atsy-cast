package com.kyawhut.atsycast.msys

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kyawhut.atsycast.msys.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msys.ui.detail.DetailActivity
import com.kyawhut.atsycast.msys.ui.home.HomeActivity
import com.kyawhut.atsycast.msys.ui.player.PlayerActivity
import com.kyawhut.atsycast.msys.utils.Constants
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/10/21
 */
object MsysApp {

    fun Context.goToMSYS(appName: String, channelLogo: String, apiKey: String) {
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
            Constants.EXTRA_API_KEY to apiKey
        )
    }

    fun Fragment.goToMSYS(appName: String, channelLogo: String, apiKey: String) {
        requireContext().goToMSYS(appName, channelLogo, apiKey)
    }

    fun Fragment.goToMSYSDetail(
        appName: String,
        channelLogo: String,
        apiKey: String,
        data: WatchLaterEntity
    ) {
        requireContext().startActivity<DetailActivity>(
            Constants.EXTRA_API_KEY to apiKey,
            Constants.EXTRA_VIDEO_DATA to data.getData<MoviesResponse>(),
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
        )
    }

    fun Fragment.goMSYSPlayer(
        appName: String,
        channelLogo: String,
        apiKey: String,
        data: RecentlyWatchEntity
    ) {
        context.startActivity<PlayerActivity>(
            Constants.EXTRA_VIDEO_ID to data.videoID,
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
