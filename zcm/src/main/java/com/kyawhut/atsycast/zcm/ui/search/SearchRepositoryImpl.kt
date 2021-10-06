package com.kyawhut.atsycast.zcm.ui.search

import android.content.Context
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import com.kyawhut.atsycast.zcm.data.network.ZCMAPI
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/7/21
 */
internal class SearchRepositoryImpl @Inject constructor(
    private val api: ZCMAPI,
    private val crashlytics: Crashlytics,
) : SearchRepository {

    override suspend fun search(
        context: Context,
        query: String,
        apiKey: String,
        callback: (NetworkResponse<List<MoviesResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) { api.search(query, apiKey) }
        if (response.isSuccess) {
            NetworkResponse.success(response.data?.moviesList?.filter {
                !it.moviesGenres.any { it.genresTitle.isAdult } || context.isAdultOpen
            } ?: listOf(), callback)
        } else NetworkResponse.error(response.error, callback)
    }
}
