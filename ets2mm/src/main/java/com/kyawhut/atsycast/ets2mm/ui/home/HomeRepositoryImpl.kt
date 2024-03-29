package com.kyawhut.atsycast.ets2mm.ui.home

import android.content.Context
import com.kyawhut.atsycast.ets2mm.data.network.Et2API
import com.kyawhut.atsycast.ets2mm.data.network.response.GenresResponse
import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.SourceType
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal class HomeRepositoryImpl @Inject constructor(
    private val api: Et2API,
    private val recentlyWatchSource: RecentlyWatchSource,
    private val watchLaterSource: WatchLaterSource,
    private val crashlytics: Crashlytics,
) : HomeRepository {

    override val isHasRecently: Boolean
        get() = recentlyWatchSource.isHasRecentlyWatch(SourceType.ET2SMM)

    override val isHasWatchLater: Boolean
        get() = watchLaterSource.isHasWatchLater(SourceType.ET2SMM)

    override suspend fun getHome(
        context: Context,
        callback: (NetworkResponse<List<GenresResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) { api.getHome() }
        if (response.isSuccess) {
            val genresList = response.data?.filter { it.genreName.isNotEmpty() }?.filter {
                !it.genreName.isAdult || context.isAdultOpen
            }?.toMutableList()
            genresList?.add(0, GenresResponse("-1", "Latest Movies"))
            genresList?.add(1, GenresResponse("0", "Latest Series"))
            NetworkResponse.success(genresList ?: listOf(), callback)
        } else response.post(callback)
    }
}
