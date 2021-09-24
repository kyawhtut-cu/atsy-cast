package com.kyawhut.atsycast.ui.home

import android.content.Context
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

    var homeFeatureList: List<HomeFeatureResponse.Data> = mutableListOf()

    fun getHomeFeatures(
        context: Context,
        callback: (NetworkResponse<List<HomeFeatureResponse.Data>>) -> Unit
    ) {
        viewModelScope {
            repo.getHomeFeatures(context, callback)
        }
    }
}
