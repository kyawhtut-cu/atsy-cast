package com.kyawhut.atsycast.msys.ui.search

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msys.utils.Constants
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
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val repository: SearchRepository
) : BaseViewModel() {

    val apiKey: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }

    fun search(
        query: String,
        callback: (NetworkResponse<List<MoviesResponse>>) -> Unit
    ) {
        viewModelScope {
            repository.search(application, query, apiKey, callback)
        }
    }
}
