package com.kyawhut.atsycast.msubpc.ui.source

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.model.VideoSourceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@HiltViewModel
internal class VideoSourceViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: VideoSourceRepository
) : BaseViewModel() {

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }
    val videoTitle: String by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_TITLE) ?: ""
    }
    val videoData: VideoResponse? by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_DATA) as VideoResponse?
    }
    val source: List<VideoSourceModel> by lazy {
        (savedStateHandle.get(Constants.EXTRA_VIDEO_SOURCE) as List<VideoSourceModel>?)
            ?: listOf()
    }
    val relatedEpisode: List<EpisodeResponse> by lazy {
        (savedStateHandle.get(Constants.EXTRA_RELATED_EPISODE) as List<EpisodeResponse>?)
            ?: listOf()
    }
    val isLive: Boolean by lazy {
        savedStateHandle.get(Constants.EXTRA_IS_LIVE) ?: false
    }

    fun isHasResume(sourceID: String): Boolean = repository.isHasResume(sourceID)
}
