package com.kyawhut.atsycast.twod.ui.home

import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.twod.data.network.response.TodayResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/23/21
 */
@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val repo: HomeRepository
) : BaseViewModel() {

    fun getHomeToday(callback: (NetworkResponse<Pair<String, TodayResponse?>>) -> Unit) {
        viewModelScope {
            repo.getHomeLottery(callback)
        }
    }
}
