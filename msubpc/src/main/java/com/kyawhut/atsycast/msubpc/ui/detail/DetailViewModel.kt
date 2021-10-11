package com.kyawhut.atsycast.msubpc.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msubpc.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.msubpc.data.network.response.MovieStreamResponse
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
@HiltViewModel
internal class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: DetailRepository
) : BaseViewModel() {

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }
    val detailData: VideoResponse? by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_DATA) as VideoResponse?
    }
    val isWatchLater: Boolean
        get() = repository.isWatchLater(detailData!!.videoId)

    fun toggleWatchLater() {
        repository.toggleWatchLater(detailData!!)
    }

    fun getMovieStream(callback: (NetworkResponse<MovieStreamResponse>) -> Unit) {
        viewModelScope {
            repository.getMovieStream(detailData!!.videoId, callback)
        }
    }

    fun getRelatedMovies(genres: String, callback: (NetworkResponse<List<VideoResponse>>) -> Unit) {
        viewModelScope {
            repository.getRelatedMovies(genres, callback)
        }
    }

    fun getSeasonEpisode(callback: (NetworkResponse<Pair<List<VideoResponse>, List<EpisodeResponse>>>) -> Unit) {
        viewModelScope {
            repository.getSeasonEpisode(detailData!!.videoId, callback)
        }
    }
}
