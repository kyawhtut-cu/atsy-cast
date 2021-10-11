package com.kyawhut.atsycast.share.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.utils.SourceType
import io.reactivex.Flowable

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@Dao
abstract class RecentlyWatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(data: RecentlyWatchEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg data: RecentlyWatchEntity): List<Long>

    @Query("select * from table_recently_watch order by created_at")
    abstract fun getAll(): Flowable<List<RecentlyWatchEntity>>

    @Query("select * from table_recently_watch where video_id = :videoID and video_source_type = :sourceType")
    abstract fun get(videoID: String, sourceType: SourceType): RecentlyWatchEntity?

    @Query("select * from table_recently_watch where video_source_type = :sourceType order by created_at")
    abstract fun get(sourceType: SourceType): List<RecentlyWatchEntity>

    @Query("select * from table_recently_watch where video_source_type = :sourceType order by created_at")
    abstract fun getLive(sourceType: SourceType): Flowable<List<RecentlyWatchEntity>>

    @Query("select count(*) from table_recently_watch where video_source_type = :sourceType")
    abstract fun getCountLiveData(sourceType: SourceType): Flowable<Int>

    @Query("select count(*) from table_recently_watch where video_source_type = :sourceType")
    abstract fun getCount(sourceType: SourceType): Int

    @Query("delete from table_recently_watch where video_source_type = :sourceType and video_id = :videoID")
    abstract fun delete(sourceType: SourceType, videoID: String)

    @Query("delete from table_recently_watch")
    abstract fun deleteAll()

}
