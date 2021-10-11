package com.kyawhut.atsycast.ets2mm.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoDetailResponse
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.ets2mm.utils.Constants
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
    private val savedStateHandle: SavedStateHandle,
    private val repository: DetailRepository,
) : BaseViewModel() {

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }
    val videoData: VideoResponse? by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_DATA) as VideoResponse?
    }
    val isWatchLater: Boolean
        get() = repository.isWatchLater(videoData!!.videoID)

    fun toggleWatchLater() {
        repository.toggleWatchLater(videoData!!)
    }

    var videoDetailResponse: VideoDetailResponse? = null

    fun getVideoDetail(callback: (NetworkResponse<VideoDetailResponse>) -> Unit) {
        viewModelScope {
            repository.getVideoDetail(
                videoData?.videoID ?: 0,
                videoData?.isMovies ?: false
            ) {
                if (it.isSuccess) {
                    videoDetailResponse = it.data
                }
                callback(it)
            }
        }
    }
}
