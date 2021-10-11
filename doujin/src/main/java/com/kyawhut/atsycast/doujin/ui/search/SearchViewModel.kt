package com.kyawhut.atsycast.doujin.ui.search

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.doujin.data.network.response.DoujinPageVideoResponse
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.doujin.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: SearchRepository
) : BaseViewModel() {

    private var page: Int = 1
    val isFirstPage: Boolean
        get() = page == 1
    private var totalPage: Int = 2
    private var isLoading: Boolean = false
    var doujinID: String = ""

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }

    fun search(
        query: String,
        callback: (NetworkResponse<DoujinPageVideoResponse>) -> Unit
    ) {
        if (page > totalPage || isLoading) return
        doujinID = ""
        viewModelScope {
            repository.search(query, page) {
                callback(it)
                isLoading = it.isLoading
                if (it.isSuccess) {
                    totalPage = it.data?.pages ?: 0
                    page += 1
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
