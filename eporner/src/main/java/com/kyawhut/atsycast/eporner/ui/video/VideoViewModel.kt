package com.kyawhut.atsycast.eporner.ui.video

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.eporner.data.network.response.EPornerVideoResponse
import com.kyawhut.atsycast.eporner.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@HiltViewModel
internal class VideoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: VideoRepository,
) : BaseViewModel() {

    private var page: Int = 1
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    private val pageKey: String by lazy {
        savedStateHandle.get(Constants.EXTRA_PAGE_KEY) ?: ""
    }
    private var isHasMoreData: Boolean = true
    private var isLoading: Boolean = false
    val isFirstPage: Boolean
        get() = page == 1

    fun getData(callback: (NetworkResponse<List<EPornerVideoResponse>>) -> Unit) {
        if (!isHasMoreData || isLoading) return
        viewModelScope {
            repository.getVideoData(pageKey, page) {
                callback(it)
                isLoading = it.isLoading
                if (it.isSuccess) {
                    isHasMoreData = it.data?.isNotEmpty() ?: false
                    if (isHasMoreData) page += 1
                }
            }
        }
    }
}
