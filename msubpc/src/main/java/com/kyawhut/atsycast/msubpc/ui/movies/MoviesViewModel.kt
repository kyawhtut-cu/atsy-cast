package com.kyawhut.atsycast.msubpc.ui.movies

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/6/21
 */
@HiltViewModel
internal class MoviesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: MoviesRepository
) : BaseViewModel() {

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    private val key: String by lazy {
        savedStateHandle.get(Constants.EXTRA_PAGE_KEY) ?: ""
    }

    fun getMovies(
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        viewModelScope {
            repo.getMovies(key, callback)
        }
    }
}
