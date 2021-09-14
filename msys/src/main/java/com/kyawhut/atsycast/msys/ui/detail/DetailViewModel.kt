package com.kyawhut.atsycast.msys.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msys.data.network.response.SeasonResponse
import com.kyawhut.atsycast.msys.utils.Constants
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
    val apiKey: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val videoData: MoviesResponse? by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_DATA) as MoviesResponse?
    }
    val isWatchLater: Boolean
        get() = repository.isWatchLater(videoData!!.moviesID)

    fun toggleWatchLater() {
        repository.toggleWatchLater(videoData!!)
    }

    fun getRelateMovies(callback: (NetworkResponse<List<MoviesResponse>>) -> Unit) {
        viewModelScope {
            repository.getRelatedMovies(
                apiKey,
                videoData!!.moviesGenres.joinToString { it.genresID.toString() },
                callback
            )
        }
    }

    fun getSeriesSeason(callback: (NetworkResponse<List<SeasonResponse>>) -> Unit) {
        viewModelScope {
            repository.getSeriesSeason(apiKey, videoData!!.moviesID, callback)
        }
    }
}
