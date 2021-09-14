package com.kyawhut.atsycast.msubpc.ui.search

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.utils.Constants
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

    val apiKey: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }

    fun search(
        query: String,
        callback: (NetworkResponse<List<Pair<String, List<VideoResponse>>>>) -> Unit
    ) {
        viewModelScope {
            repository.search(query, apiKey, callback)
        }
    }
}
