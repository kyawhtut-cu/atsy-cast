package com.kyawhut.atsycast.zcm.ui.home

import android.content.Context
import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.SourceType
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import com.kyawhut.atsycast.zcm.data.network.ZCMAPI
import com.kyawhut.atsycast.zcm.data.network.response.GenresResponse
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal class HomeRepositoryImpl @Inject constructor(
    private val api: ZCMAPI,
    private val recentlyWatchSource: RecentlyWatchSource,
    private val watchLaterSource: WatchLaterSource
) : HomeRepository {

    override val isHasRecently: Boolean
        get() = recentlyWatchSource.isHasRecentlyWatch(SourceType.ZCM)

    override val isHasWatchLater: Boolean
        get() = watchLaterSource.isHasWatchLater(SourceType.ZCM)

    override suspend fun getHome(
        context: Context,
        apiKey: String,
        callback: (NetworkResponse<List<GenresResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute { api.getHome(apiKey) }
        if (response.isSuccess) {
            val genresList = response.data?.filter { it.genresTitle.isNotEmpty() }?.filter {
                !it.genresTitle.isAdult || context.isAdultOpen
            }?.toMutableList()
            genresList?.add(0, GenresResponse(0, "Latest"))
            NetworkResponse.success(genresList ?: listOf(), callback)
        } else response.post(callback)
    }
}
