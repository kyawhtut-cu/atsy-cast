package com.kyawhut.atsycast.ets2mm.ui.movies

import android.content.Context
import com.kyawhut.atsycast.ets2mm.data.network.Et2API
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.extension.Extension.isAdultOpen
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
internal class MoviesRepositoryImpl @Inject constructor(
    private val api: Et2API,
    private val crashlytics: Crashlytics,
) : MoviesRepository {

    override suspend fun getMovies(
        context: Context,
        genresID: String,
        page: Int,
        callback: (NetworkResponse<List<VideoResponse>>) -> Unit
    ) {
        NetworkResponse.loading(callback)
        val response = execute(crashlytics) {
            when (genresID) {
                "-1" -> api.getLatestMovies(page)
                "0" -> api.getLatestTvSeries(page)
                else -> api.getVideoByGenresID(genresID, page)
            }
        }
        if (response.isSuccess) {
            NetworkResponse.success(
                response.data?.filter {
                    !it.videoTitle.isAdult || context.isAdultOpen
                } ?: listOf(),
                callback
            )
        } else response.post(callback)
    }
}
