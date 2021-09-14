package com.kyawhut.atsycast.share.di

import android.content.Context
import com.kyawhut.atsycast.share.db.AppDatabase
import com.kyawhut.atsycast.share.db.source.RecentlyWatchSource
import com.kyawhut.atsycast.share.db.source.RecentlyWatchSourceImpl
import com.kyawhut.atsycast.share.db.source.WatchLaterSource
import com.kyawhut.atsycast.share.db.source.WatchLaterSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@Module
@InstallIn(SingletonComponent::class)
object ShareModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.provideDatabase(context)
    }

    @Provides
    fun provideRecentlyWatch(db: AppDatabase): RecentlyWatchSource {
        return RecentlyWatchSourceImpl(db.recentlyWatch)
    }

    @Provides
    fun provideWatchLater(db: AppDatabase): WatchLaterSource {
        return WatchLaterSourceImpl(db.watchLater)
    }

}
