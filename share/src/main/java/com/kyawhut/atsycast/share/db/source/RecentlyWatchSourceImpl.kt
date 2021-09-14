package com.kyawhut.atsycast.share.db.source

import androidx.lifecycle.LiveData
import com.kyawhut.atsycast.share.db.dao.RecentlyWatchDao
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.utils.SourceType

/**
 * @author kyawhtut
 * @date 9/8/21
 */
class RecentlyWatchSourceImpl(private val dao: RecentlyWatchDao) : RecentlyWatchSource {

    override fun insert(block: RecentlyWatchEntity.Builder.() -> Unit): Long {
        return dao.insert(RecentlyWatchEntity.Builder().apply(block).build())
    }

    override fun insert(vararg block: RecentlyWatchEntity.Builder.() -> Unit): List<Long> {
        return dao.insert(*block.map {
            RecentlyWatchEntity.Builder().also(it).build()
        }.toTypedArray())
    }

    override fun get(videoID: String, sourceType: SourceType): RecentlyWatchEntity? {
        return dao.get(videoID, sourceType)
    }

    override fun get(sourceType: SourceType): List<RecentlyWatchEntity> {
        return dao.get(sourceType)
    }

    override fun getLive(sourceType: SourceType): LiveData<List<RecentlyWatchEntity>> {
        return dao.getLive(sourceType)
    }

    override fun isHasRecentlyWatch(sourceType: SourceType): Boolean {
        return dao.getCount(sourceType) != 0
    }

    override fun delete(videoID: String, sourceType: SourceType) {
        dao.delete(sourceType, videoID)
    }

    override fun deleteAll() {
        dao.deleteAll()
    }
}
