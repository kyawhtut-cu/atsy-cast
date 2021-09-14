package com.kyawhut.atsycast.msubpc.ui.football

import com.kyawhut.atsycast.msubpc.data.network.response.FootballResponse
import com.kyawhut.atsycast.msubpc.data.network.response.FootballStreamResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal interface FootballRepository {

    suspend fun getFootball(
        apiKey: String,
        callback: (NetworkResponse<List<FootballResponse.Data>>) -> Unit
    )

    suspend fun getFootballStream(
        footballID: Int,
        apiKey: String,
        callback: (NetworkResponse<FootballStreamResponse>) -> Unit
    )
}
