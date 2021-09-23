package com.kyawhut.atsycast.twod.ui.home

import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.twod.data.network.TwoDAPI
import com.kyawhut.atsycast.twod.data.network.response.TodayResponse
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/23/21
 */
internal class HomeRepositoryImpl @Inject constructor(
    private val lotteryAPI: TwoDAPI
) : HomeRepository {

    override suspend fun getHomeLottery(callback: (NetworkResponse<Pair<String, TodayResponse?>>) -> Unit) {
        val luckyNumber = execute { lotteryAPI.getLuckyNumber().luckyNumber }
        val todayResponse = execute { lotteryAPI.getToday() }
        if (luckyNumber.isSuccess || todayResponse.isSuccess) {
            NetworkResponse.success((luckyNumber.data ?: "") to todayResponse.data, callback)
        } else if (luckyNumber.isError) NetworkResponse.error(luckyNumber.error, callback)
        else if (todayResponse.isError) NetworkResponse.error(todayResponse.error, callback)
    }
}
