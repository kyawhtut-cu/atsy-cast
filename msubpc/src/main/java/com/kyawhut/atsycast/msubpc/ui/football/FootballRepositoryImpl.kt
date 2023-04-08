package com.kyawhut.atsycast.msubpc.ui.football

import com.kyawhut.atsycast.msubpc.data.network.MSubAPI
import com.kyawhut.atsycast.msubpc.data.network.response.FootballResponse
import com.kyawhut.atsycast.msubpc.data.network.response.FootballStreamResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class FootballRepositoryImpl @Inject constructor(
    private val api: MSubAPI,
    private val crashlytics: Crashlytics,
) : FootballRepository {

    override suspend fun getFootball(
        callback: (NetworkResponse<List<FootballResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) { api.getFootball() }
        if (response.isSuccess) {
            NetworkResponse.success(response.data ?: listOf(), callback)
        } else {
            NetworkResponse.error(response.error, callback)
        }
    }

    override suspend fun getFootballStream(
        footballID: Int,
        callback: (NetworkResponse<List<FootballStreamResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) { api.getFootballStream(footballID) }
        response.post(callback)
    }
}
