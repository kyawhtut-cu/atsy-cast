package com.kyawhut.atsycast.msubpc.ui.football

import com.kyawhut.atsycast.msubpc.data.network.MSubAPI
import com.kyawhut.atsycast.msubpc.data.network.response.FootballResponse
import com.kyawhut.atsycast.msubpc.data.network.response.FootballStreamResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal class FootballRepositoryImpl @Inject constructor(
    private val api: MSubAPI
) : FootballRepository {

    override suspend fun getFootball(
        callback: (NetworkResponse<List<FootballResponse.Data>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getFootball() }
        if (response.isSuccess) {
            NetworkResponse.success(response.data?.football ?: listOf(), callback)
        } else {
            NetworkResponse.error(response.error, callback)
        }
    }

    override suspend fun getFootballStream(
        footballID: Int,
        callback: (NetworkResponse<FootballStreamResponse>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getFootballStream(footballID) }
        response.post(callback)
    }
}
