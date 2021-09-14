package com.kyawhut.atsycast.ui.home

import com.kyawhut.atsycast.data.network.response.HomeFeatureResponse
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeRepository
) : BaseViewModel() {

    fun getHomeFeatures(callback: (NetworkResponse<List<HomeFeatureResponse>>) -> Unit) {
        viewModelScope {
            repo.getHomeFeatures(callback)
        }
    }
}
