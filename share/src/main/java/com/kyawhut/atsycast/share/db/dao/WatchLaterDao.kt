package com.kyawhut.atsycast.share.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.utils.SourceType
import io.reactivex.Flowable

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@Dao
abstract class WatchLaterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(data: WatchLaterEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg data: WatchLaterEntity): List<Long>

    @Query("select * from table_watch_later order by created_at desc")
    abstract fun getAll(): Flowable<List<WatchLaterEntity>>

    @Query("select * from table_watch_later where video_source_type = :sourceType order by created_at desc")
    abstract fun get(sourceType: SourceType): List<WatchLaterEntity>

    @Query("select * from table_watch_later where video_source_type = :sourceType order by created_at desc")
    abstract fun getLive(sourceType: SourceType): Flowable<List<WatchLaterEntity>>

    @Query("select count(*) from table_watch_later where video_source_type = :sourceType")
    abstract fun getCountLiveData(sourceType: SourceType): Flowable<Int>

    @Query("select count(*) from table_watch_later where video_source_type = :sourceType")
    abstract fun getCount(sourceType: SourceType): Int

    @Query("delete from table_watch_later where id = :id and video_source_type = :sourceType")
    abstract fun delete(id: Int, sourceType: SourceType)

    @Query("delete from table_watch_later")
    abstract fun deleteAll()
}
