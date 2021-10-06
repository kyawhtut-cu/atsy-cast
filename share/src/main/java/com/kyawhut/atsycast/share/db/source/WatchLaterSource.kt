package com.kyawhut.atsycast.share.db.source

import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.utils.SourceType
import io.reactivex.Flowable

/**
 * @author kyawhtut
 * @date 9/8/21
 */
interface WatchLaterSource {

    fun insert(block: WatchLaterEntity.Builder.() -> Unit): Long

    fun insert(vararg block: WatchLaterEntity.Builder.() -> Unit): List<Long>

    fun getAll(): Flowable<List<WatchLaterEntity>>

    fun get(sourceType: SourceType): List<WatchLaterEntity>

    fun getLive(sourceType: SourceType): Flowable<List<WatchLaterEntity>>

    fun isHasWatchLater(sourceType: SourceType): Boolean

    fun delete(id: Int, sourceType: SourceType)

    fun deleteAll()
}
