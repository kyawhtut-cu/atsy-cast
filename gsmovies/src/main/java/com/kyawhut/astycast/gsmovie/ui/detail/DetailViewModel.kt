package com.kyawhut.astycast.gsmovie.ui.detail

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.astycast.gsmovie.data.network.response.VideoDetailResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.astycast.gsmovie.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@HiltViewModel
internal class DetailViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val repository: DetailRepository,
) : BaseViewModel() {

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }
    val route: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val videoData: VideoResponse.Data? by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_DATA) as VideoResponse.Data?
    }
    var videoDetail: VideoDetailResponse.Data? = null

    val isWatchLater: Boolean
        get() = repository.isWatchLater(route, videoData!!.videoID)

    fun toggleWatchLater() {
        repository.toggleWatchLater(route, videoData!!)
    }

    fun getVideoDetail(callback: (NetworkResponse<VideoDetailResponse.Data>) -> Unit) {
        viewModelScope {
            repository.getVideoDetail(
                application,
                route,
                videoData!!.videoID,
                callback
            )
        }
    }
}
