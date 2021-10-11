package com.kyawhut.atsycast.gs_mm.ui.home

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.gs_mm.data.network.response.DrawerResponse
import com.kyawhut.atsycast.gs_mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/3/21
 */
@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val homeRepository: HomeRepository,
) : BaseViewModel() {

    var categoryList: List<DrawerResponse.Data> = mutableListOf()

    val route: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }

    val isHasWatchLater: Boolean
        get() = homeRepository.isHasWatchLater(route)

    val isHasRecently: Boolean
        get() = homeRepository.isHasRecently(route)

    fun getHome(callback: (NetworkResponse<List<DrawerResponse.Data>>) -> Unit) {
        viewModelScope {
            homeRepository.getHome(application, route, callback)
        }
    }
}
