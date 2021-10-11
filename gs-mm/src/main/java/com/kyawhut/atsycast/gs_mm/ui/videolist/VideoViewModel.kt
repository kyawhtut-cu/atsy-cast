package com.kyawhut.atsycast.gs_mm.ui.videolist

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.gs_mm.data.network.response.DrawerDetailResponse
import com.kyawhut.atsycast.gs_mm.utils.Constants
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

    val route: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }
    private val drawerKey: String by lazy {
        savedStateHandle.get(Constants.EXTRA_PAGE_KEY) ?: ""
    }

    fun getVideoList(callback: (NetworkResponse<List<DrawerDetailResponse.Data>>) -> Unit) {
        viewModelScope {
            repository.getVideoList(application, drawerKey, route, callback)
        }
    }
}
