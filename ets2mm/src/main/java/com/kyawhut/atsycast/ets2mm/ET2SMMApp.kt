package com.kyawhut.atsycast.ets2mm

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kyawhut.atsycast.ets2mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.ets2mm.ui.detail.DetailActivity
import com.kyawhut.atsycast.ets2mm.ui.home.HomeActivity
import com.kyawhut.atsycast.ets2mm.ui.player.PlayerActivity
import com.kyawhut.atsycast.ets2mm.utils.Constants
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.utils.extension.put
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/15/21
 */
object ET2SMMApp {

    private fun Context.setAPIKey(apiKey: String) {
        with(PreferenceManager.getDefaultSharedPreferences(this)) {
            put(Constants.EXTRA_API_KEY, apiKey)
        }
    }

    internal fun Context.clearAPIKey() {
        setAPIKey("")
    }

    fun Context.goToETS2MM(appName: String, apiKey: String, channelLogo: String) {
        setAPIKey(apiKey)
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
        )
    }

    fun Fragment.goToETS2MM(appName: String, apiKey: String, channelLogo: String) {
        requireContext().goToETS2MM(appName, apiKey, channelLogo)
    }

    fun Fragment.goToETS2MMDetail(
        appName: String,
        apiKey: String,
        channelLogo: String,
        data: WatchLaterEntity
    ) {
        requireContext().setAPIKey(apiKey)
        requireContext().startActivity<DetailActivity>(
            Constants.EXTRA_VIDEO_DATA to data.getData<VideoResponse>(),
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
        )
    }

    fun Fragment.goToETS2MMPlayer(
        appName: String,
        apiKey: String,
        channelLogo: String,
        data: RecentlyWatchEntity
    ) {
        context.startActivity<PlayerActivity>(
            Constants.EXTRA_VIDEO_ID to data.videoID.toInt(),
            Constants.EXTRA_IS_RESUME to true,
            Constants.EXTRA_IS_ADULT to data.isAdult,
            Constants.EXTRA_VIDEO_TITLE to data.videoTitle,
            Constants.EXTRA_VIDEO_COVER to data.videoCover,
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
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
