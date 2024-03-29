package com.kyawhut.atsycast.msubpc.ui.series

import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal interface SeriesRepository {

    suspend fun getSeries(
        key: String,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    )
}
