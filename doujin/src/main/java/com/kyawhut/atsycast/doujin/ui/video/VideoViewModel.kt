package com.kyawhut.atsycast.doujin.ui.video

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.doujin.R
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoResponse
import com.kyawhut.atsycast.doujin.utils.Constants
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

    private var page: Int = -1
    val isHot: Boolean
        get() = page == -1
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    private val pageKey: String by lazy {
        savedStateHandle.get(Constants.EXTRA_PAGE_KEY) ?: ""
    }
    private var isHasMoreData: Boolean = true
    private var isLoading: Boolean = false
    val isFirstPage: Boolean
        get() = page == 1 || page == -1
    var doujinID: String = ""

    fun getData(context: Context, callback: (NetworkResponse<List<DoujinVideoResponse>>) -> Unit) {
        doujinID = ""
        if (pageKey != context.getString(R.string.lblHotVideosKey) && page == -1) {
            page = 1
        }
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

    fun getVideoDetail(
        doujinID: String = this.doujinID,
        callback: (NetworkResponse<DoujinVideoDetailResponse>) -> Unit
    ) {
        this.doujinID = doujinID
        viewModelScope {
            repository.getVideoDetail(doujinID, callback)
        }
    }
}
