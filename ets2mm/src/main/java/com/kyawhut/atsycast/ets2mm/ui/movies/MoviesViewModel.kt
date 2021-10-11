package com.kyawhut.atsycast.ets2mm.ui.movies

import android.app.Application
import androidx.lifecycle.SavedStateHandle
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
internal class MoviesViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val repository: MoviesRepository
) : BaseViewModel() {

    private var page: Int = 1
    val isFirstPage: Boolean
        get() = page == 1
    private var isLoading: Boolean = false
    private var isHasMoreData: Boolean = true
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }
    private val genresID: String by lazy {
        savedStateHandle.get(Constants.EXTRA_PAGE_KEY) ?: "0"
    }

    fun getMovies(callback: (NetworkResponse<List<VideoResponse>>) -> Unit) {
        if (isLoading || !isHasMoreData) return
        viewModelScope {
            repository.getMovies(application, genresID, page) {
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
