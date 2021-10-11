package com.kyawhut.atsycast.share.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
    version = 3
)
@TypeConverters(DBConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    ALTER TABLE `table_recently_watch` ADD `is_adult` INTEGER NOT NULL DEFAULT 0;
                """.trimIndent()
                )

                database.execSQL(
                    """
                    ALTER TABLE `table_recently_watch` ADD `updated_at` TEXT NOT NULL DEFAULT '2021-09-22 02:00 pm';
                """.trimIndent()
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    ALTER TABLE `table_recently_watch` ADD `video_subtitle` TEXT NOT NULL DEFAULT "";
                """.trimIndent()
                )
            }
        }

        fun provideDatabase(context: Context): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "atsy-cast.db"
        ).allowMainThreadQueries().addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()


    }

    abstract val recentlyWatch: RecentlyWatchDao
    abstract val watchLater: WatchLaterDao
}
