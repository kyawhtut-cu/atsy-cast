package com.kyawhut.atsycast.share.db.source

import com.kyawhut.atsycast.share.db.dao.WatchLaterDao
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.utils.SourceType
import io.reactivex.Flowable

/**
 * @author kyawhtut
 * @date 9/8/21
 */
class WatchLaterSourceImpl(private val dao: WatchLaterDao) : WatchLaterSource {

    override fun insert(block: WatchLaterEntity.Builder.() -> Unit): Long {
        return dao.insert(WatchLaterEntity.Builder().also(block).build())
    }

    override fun insert(vararg block: WatchLaterEntity.Builder.() -> Unit): List<Long> {
        return dao.insert(*block.map {
            WatchLaterEntity.Builder().also(it).build()
        }.toTypedArray())
    }

    override fun get(sourceType: SourceType): List<WatchLaterEntity> {
        return dao.get(sourceType)
    }

    override fun getAll(): Flowable<List<WatchLaterEntity>> {
        return dao.getAll()
    }

    override fun getLive(sourceType: SourceType): Flowable<List<WatchLaterEntity>> {
        return dao.getLive(sourceType)
    }

    override fun isHasWatchLater(sourceType: SourceType): Boolean {
        return dao.getCount(sourceType) != 0
    }

    override fun delete(id: Int, sourceType: SourceType) {
        dao.delete(id, sourceType)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }
}
