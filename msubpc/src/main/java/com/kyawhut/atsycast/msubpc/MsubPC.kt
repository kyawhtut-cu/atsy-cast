package com.kyawhut.atsycast.msubpc

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.ui.detail.DetailActivity
import com.kyawhut.atsycast.msubpc.ui.home.HomeActivity
import com.kyawhut.atsycast.msubpc.ui.player.PlayerActivity
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.utils.extension.put
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/9/21
 */
object MsubPC {

    private fun Context.setAPIKey(apiKey: String) {
        with(PreferenceManager.getDefaultSharedPreferences(this)) {
            put(Constants.EXTRA_API_KEY, apiKey)
        }
    }

    internal fun Context.clearAPIKey() {
        setAPIKey("")
    }

    fun Context.goToMsubPC(appName: String, channelLogo: String, apiKey: String) {
        setAPIKey(apiKey)
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
        )
    }

    fun Fragment.goToMsubPC(appName: String, channelLogo: String, apiKey: String) {
        requireContext().goToMsubPC(appName, channelLogo, apiKey)
    }

    fun Fragment.goToMSubPCDetail(
        appName: String,
        channelLogo: String,
        apiKey: String,
        data: WatchLaterEntity
    ) {
        requireContext().setAPIKey(apiKey)
        requireContext().startActivity<DetailActivity>(
            Constants.EXTRA_VIDEO_DATA to data.getData<VideoResponse>(),
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
        )
    }

    fun Fragment.goToMSubPCPlayer(
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
