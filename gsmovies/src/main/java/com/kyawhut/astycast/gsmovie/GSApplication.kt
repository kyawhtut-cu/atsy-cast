package com.kyawhut.astycast.gsmovie

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kyawhut.astycast.gsmovie.data.network.response.VideoEpisodeResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.astycast.gsmovie.ui.detail.DetailActivity
import com.kyawhut.astycast.gsmovie.ui.home.HomeActivity
import com.kyawhut.astycast.gsmovie.ui.player.PlayerActivity
import com.kyawhut.astycast.gsmovie.utils.Constants
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/29/21
 */
object GSApplication {

    private fun goToHome(context: Context, appName: String, apiKey: String) {
        context.startActivity<HomeActivity>(
            Constants.EXTRA_API_KEY to apiKey,
            Constants.EXTRA_APP_NAME to appName,
        )
    }

    fun FragmentActivity.goToGSHome(apiKey: String, appName: String) {
        goToHome(this, appName, apiKey)
    }

    fun Fragment.goToGSHome(apiKey: String, appName: String) {
        goToHome(requireContext(), appName, apiKey)
    }

    fun Fragment.goToGSDetail(appName: String, apiKey: String, data: WatchLaterEntity) {
        requireContext().startActivity<DetailActivity>(
            Constants.EXTRA_API_KEY to apiKey,
            Constants.EXTRA_VIDEO_DATA to data.getData<VideoResponse.Data>(),
            Constants.EXTRA_APP_NAME to appName,
        )
    }

    fun Fragment.goToGSPlayer(appName: String, apiKey: String, data: RecentlyWatchEntity) {
        context.startActivity<PlayerActivity>(
            Constants.EXTRA_VIDEO_ID to data.videoID.toInt(),
            Constants.EXTRA_IS_RESUME to true,
            Constants.EXTRA_VIDEO_TITLE to data.videoTitle,
            Constants.EXTRA_VIDEO_COVER to data.videoCover,
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_IS_ADULT to data.isAdult,
            Constants.EXTRA_VIDEO_SOURCE to VideoSourceModel(
                data.videoID.toInt(),
                data.videoTitle,
                null,
                data.videoURL,
                data.videoAgent,
                data.videoCookies,
                data.videoCustomHeader
            ),
            Constants.EXTRA_RELATED_EPISODE to Gson().fromJson<List<VideoEpisodeResponse>>(
                data.videoRelatedVideo,
                object : TypeToken<List<VideoEpisodeResponse>>() {}.type
            )
        )
    }
}
