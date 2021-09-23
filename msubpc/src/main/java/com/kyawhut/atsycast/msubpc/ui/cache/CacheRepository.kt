package com.kyawhut.atsycast.msubpc.ui.cache

import android.content.Context
import androidx.lifecycle.LiveData
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity

/**
 * @author kyawhtut
 * @date 9/9/21
 */
internal interface CacheRepository {

    fun getRecentlyWatch(context: Context): LiveData<List<RecentlyWatchEntity>>

    fun getWatchLater(context: Context): LiveData<List<VideoResponse>>

}
