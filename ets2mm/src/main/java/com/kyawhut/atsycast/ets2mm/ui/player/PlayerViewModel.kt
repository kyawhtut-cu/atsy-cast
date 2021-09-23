package com.kyawhut.atsycast.ets2mm.ui.player

import androidx.lifecycle.SavedStateHandle
import com.google.gson.Gson
import com.kyawhut.atsycast.ets2mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.ets2mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.utils.SourceType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@HiltViewModel
internal class PlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: PlayerRepository,
) : BaseViewModel() {

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    private val isAdult: Boolean by lazy {
        savedStateHandle.get(Constants.EXTRA_IS_ADULT) ?: false
    }
    var videoID: Int = savedStateHandle.get(Constants.EXTRA_VIDEO_ID) ?: 0
    var isResume: Boolean = savedStateHandle.get(Constants.EXTRA_IS_RESUME) ?: false
    var videoTitle: String = savedStateHandle.get(Constants.EXTRA_VIDEO_TITLE) ?: ""
    var videoCover: String = savedStateHandle.get(Constants.EXTRA_VIDEO_COVER) ?: ""
    var videoSource: VideoSourceModel? =
        savedStateHandle.get(Constants.EXTRA_VIDEO_SOURCE) as VideoSourceModel?
    val episodeList: List<EpisodeResponse> by lazy {
        (savedStateHandle.get(Constants.EXTRA_RELATED_EPISODE) as List<EpisodeResponse>?)
            ?: listOf()
    }

    val lastPosition: Long
        get() = repository.getLastPosition(videoID, isResume)

    fun updatePlayerPositionCache(lastPosition: Long, duration: Long) {
        if (duration < 0) return
        repository.deleteRecentlyWatch(videoID)
        if (lastPosition >= duration) return
        repository.insertLastPosition {
            videoID = "${this@PlayerViewModel.videoID}"
            videoTitle = this@PlayerViewModel.videoTitle
            videoCover = this@PlayerViewModel.videoCover
            videoDuration = duration
            videoLastPosition = lastPosition
            videoURL = this@PlayerViewModel.videoSource!!.url
            videoAgent = this@PlayerViewModel.videoSource!!.agent
            videoCookies = this@PlayerViewModel.videoSource!!.cookies
            videoCustomHeader = this@PlayerViewModel.videoSource!!.customHeader
            videoRelatedVideo = Gson().toJson(episodeList)
            videoSourceType = SourceType.ET2SMM
            isAdult = this@PlayerViewModel.isAdult
        }
    }
}
