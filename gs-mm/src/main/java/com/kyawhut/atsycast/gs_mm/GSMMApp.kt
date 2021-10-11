package com.kyawhut.atsycast.gs_mm

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kyawhut.atsycast.gs_mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.gs_mm.ui.detail.DetailActivity
import com.kyawhut.atsycast.gs_mm.ui.home.HomeActivity
import com.kyawhut.atsycast.gs_mm.ui.player.PlayerActivity
import com.kyawhut.atsycast.gs_mm.utils.Constants
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 10/8/21
 */
object GSMMApp {

    private fun goToGSMMHome(
        context: Context,
        appName: String,
        channelLogo: String,
        route: String
    ) {
        context.startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_API_KEY to route,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
        )
    }

    fun Fragment.goToGSMMHome(appName: String, channelLogo: String, route: String) {
        goToGSMMHome(requireContext(), appName, channelLogo, route)
    }

    fun Fragment.goToGSMMDetail(
        appName: String,
        channelLogo: String,
        route: String,
        data: WatchLaterEntity
    ) {
        context.startActivity<DetailActivity>(
            Constants.EXTRA_API_KEY to route,
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
            Constants.EXTRA_VIDEO_DATA to data.getData<VideoResponse>()
        )
    }

    fun Fragment.goToGSMMPlayer(
        appName: String,
        channelLogo: String,
        route: String,
        data: RecentlyWatchEntity
    ) {
        context.startActivity<PlayerActivity>(
            Constants.EXTRA_VIDEO_ID to data.videoID,
            Constants.EXTRA_IS_RESUME to true,
            Constants.EXTRA_VIDEO_TITLE to data.videoTitle,
            Constants.EXTRA_VIDEO_COVER to data.videoCover,
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_API_KEY to route,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
            Constants.EXTRA_IS_ADULT to data.isAdult,
            Constants.EXTRA_VIDEO_SOURCE to VideoSourceModel(
                data.videoID,
                data.videoTitle,
                null,
                data.videoURL,
                data.videoAgent,
                data.videoCookies,
                data.videoCustomHeader,
                subTitle = data.videoSubtitle
            ),
            Constants.EXTRA_RELATED_EPISODE to Gson().fromJson<List<EpisodeResponse.Data>>(
                data.videoRelatedVideo,
                object : TypeToken<List<EpisodeResponse.Data>>() {}.type
            )
        )
    }
}
