package com.kyawhut.atsycast.share.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kyawhut.atsycast.share.db.converter.DBConverter
import com.kyawhut.atsycast.share.db.dao.RecentlyWatchDao
import com.kyawhut.atsycast.share.db.dao.WatchLaterDao
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@Database(
    entities = [RecentlyWatchEntity::class, WatchLaterEntity::class],
    version = 1
)
@TypeConverters(DBConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun provideDatabase(context: Context): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "atsy-cast.db"
        ).fallbackToDestructiveMigration().allowMainThreadQueries().build()


    }

    abstract val recentlyWatch: RecentlyWatchDao
    abstract val watchLater: WatchLaterDao
}
