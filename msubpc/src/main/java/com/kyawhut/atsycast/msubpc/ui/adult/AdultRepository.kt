package com.kyawhut.atsycast.msubpc.ui.adult

import com.kyawhut.atsycast.msubpc.data.network.response.AdultResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal interface AdultRepository {

    suspend fun getAdult(
        callback: (NetworkResponse<List<AdultResponse>>) -> Unit
    )
}
