package com.kyawhut.atsycast.msys.ui.genres

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
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
internal class MoviesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MoviesRepository
) : BaseViewModel() {

    var page: Int = 0
        private set
    private var isLoading: Boolean = false
    private var isHasMoreData: Boolean = true
    val apiKey: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val genresID: Int by lazy {
        savedStateHandle.get(Constants.EXTRA_PAGE_KEY) ?: 0
    }

    fun getMovies(callback: (NetworkResponse<List<MoviesResponse>>) -> Unit) {
        if (isLoading || !isHasMoreData) return
        viewModelScope {
            repository.getMovies(genresID, apiKey, page) {
                callback(it)
                isLoading = it.isLoading
                if (it.isSuccess) {
                    isHasMoreData = (it.data ?: listOf()).isNotEmpty()
                    if (isHasMoreData) page += 1
                }
            }
        }
    }
}
