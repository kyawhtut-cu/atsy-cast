package com.kyawhut.atsycast.eporner.ui.search

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.eporner.data.network.response.EPornerVideoResponse
import com.kyawhut.atsycast.eporner.utils.Constants
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
    private var isHasMoreData: Boolean = true
    private var isLoading: Boolean = false

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }

    fun search(
        query: String,
        callback: (NetworkResponse<List<EPornerVideoResponse>>) -> Unit
    ) {
        if (!isHasMoreData || isLoading) return
        viewModelScope {
            repository.search(query, page) {
                callback(it)
                isLoading = it.isLoading
                if (it.isSuccess) {
                    isHasMoreData = it.data?.isNotEmpty() == true
                    page += 1
                }
            }
        }
    }
}
