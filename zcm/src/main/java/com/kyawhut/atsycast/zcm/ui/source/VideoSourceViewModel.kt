package com.kyawhut.atsycast.zcm.ui.source

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.zcm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse
import com.kyawhut.atsycast.zcm.utils.Constants
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
    val videoData: MoviesResponse? by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_DATA) as MoviesResponse?
    }
    val source: List<VideoSourceModel> by lazy {
        (savedStateHandle.get(Constants.EXTRA_VIDEO_SOURCE) as List<VideoSourceModel>?)
            ?: listOf()
    }
    val relatedEpisode: List<EpisodeResponse> by lazy {
        (savedStateHandle.get(Constants.EXTRA_RELATED_EPISODE) as List<EpisodeResponse>?)
            ?: listOf()
    }

    fun isHasResume(sourceID: String): Boolean = repository.isHasResume(sourceID)
}
