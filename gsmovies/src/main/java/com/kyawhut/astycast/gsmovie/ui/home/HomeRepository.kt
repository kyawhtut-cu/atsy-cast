package com.kyawhut.astycast.gsmovie.ui.home

import android.content.Context
import com.kyawhut.astycast.gsmovie.data.network.response.CategoryResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface HomeRepository {

    suspend fun getHome(
        context: Context,
        route: String,
        callback: (NetworkResponse<List<CategoryResponse.Data>>) -> Unit
    )
}
