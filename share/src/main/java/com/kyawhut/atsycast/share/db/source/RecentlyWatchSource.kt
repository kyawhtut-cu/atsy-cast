package com.kyawhut.atsycast.share.db.source

import androidx.lifecycle.LiveData
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.utils.SourceType
import io.reactivex.Flowable

/**
 * @author kyawhtut
 * @date 9/8/21
 */
interface RecentlyWatchSource {

    fun insert(block: RecentlyWatchEntity.Builder.() -> Unit): Long

    fun insert(vararg block: RecentlyWatchEntity.Builder.() -> Unit): List<Long>

    fun isHasRecentlyWatch(sourceType: SourceType): Boolean

    fun get(videoID: String, sourceType: SourceType): RecentlyWatchEntity?

    fun get(sourceType: SourceType): List<RecentlyWatchEntity>

    fun getLive(sourceType: SourceType): Flowable<List<RecentlyWatchEntity>>

    fun delete(videoID: String, sourceType: SourceType)

    fun deleteAll()
}
