package com.kyawhut.astycast.gsmovie.ui.video

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.astycast.gsmovie.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@HiltViewModel
internal class VideoViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val repository: VideoRepository
) : BaseViewModel() {

    private var page: Int = 0
    val isFirstPage: Boolean
        get() = page == 0
    private var isLoading: Boolean = false
    private var isHasMoreData: Boolean = true
    val route: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    private val categoryID: Int by lazy {
        savedStateHandle.get(Constants.EXTRA_PAGE_KEY) ?: 0
    }

    fun getVideoList(callback: (NetworkResponse<List<VideoResponse.Data>>) -> Unit) {
        if (isLoading || !isHasMoreData) return
        viewModelScope {
            repository.getVideoList(application, categoryID, route, page) {
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
