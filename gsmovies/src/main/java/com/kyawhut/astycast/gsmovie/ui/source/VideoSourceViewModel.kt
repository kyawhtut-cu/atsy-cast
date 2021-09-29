package com.kyawhut.astycast.gsmovie.ui.source

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.astycast.gsmovie.data.network.response.VideoDetailResponse
import com.kyawhut.astycast.gsmovie.data.network.response.VideoEpisodeResponse
import com.kyawhut.astycast.gsmovie.utils.Constants
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
    val route: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val videoTitle: String by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_TITLE) ?: ""
    }
    val videoData: VideoDetailResponse.Data? by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_DATA) as VideoDetailResponse.Data?
    }
    val source: List<VideoSourceModel> by lazy {
        (savedStateHandle.get(Constants.EXTRA_VIDEO_SOURCE) as List<VideoSourceModel>?)
            ?: listOf()
    }
    val relatedEpisode: List<VideoEpisodeResponse> by lazy {
        (savedStateHandle.get(Constants.EXTRA_RELATED_EPISODE) as List<VideoEpisodeResponse>?)
            ?: listOf()
    }

    fun isHasResume(sourceID: Int): Boolean = repository.isHasResume(route, sourceID)
}
