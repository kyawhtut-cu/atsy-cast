package com.kyawhut.atsycast.zcm.ui.genres

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse
import com.kyawhut.atsycast.zcm.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@HiltViewModel
internal class MoviesViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val repository: MoviesRepository
) : BaseViewModel() {

    private var page: Int = 0
    val isFirstPage: Boolean
        get() = page == 0
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
            repository.getMovies(application, genresID, apiKey, page) {
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
