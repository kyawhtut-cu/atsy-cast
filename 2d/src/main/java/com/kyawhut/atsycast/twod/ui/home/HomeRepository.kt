package com.kyawhut.atsycast.twod.ui.home

import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.twod.data.network.response.TodayResponse

/**
 * @author kyawhtut
 * @date 9/23/21
 */
internal interface HomeRepository {

    suspend fun getHomeLottery(callback: (NetworkResponse<Pair<String, TodayResponse?>>) -> Unit)
}
