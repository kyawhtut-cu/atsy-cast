package com.kyawhut.atsycast.zcm.data.di

import com.kyawhut.atsycast.zcm.ui.cache.CacheRepository
import com.kyawhut.atsycast.zcm.ui.cache.CacheRepositoryImpl
import com.kyawhut.atsycast.zcm.ui.detail.DetailRepository
import com.kyawhut.atsycast.zcm.ui.detail.DetailRepositoryImpl
import com.kyawhut.atsycast.zcm.ui.genres.MoviesRepository
import com.kyawhut.atsycast.zcm.ui.genres.MoviesRepositoryImpl
import com.kyawhut.atsycast.zcm.ui.home.HomeRepository
import com.kyawhut.atsycast.zcm.ui.home.HomeRepositoryImpl
import com.kyawhut.atsycast.zcm.ui.player.PlayerRepository
import com.kyawhut.atsycast.zcm.ui.player.PlayerRepositoryImpl
import com.kyawhut.atsycast.zcm.ui.search.SearchRepository
import com.kyawhut.atsycast.zcm.ui.search.SearchRepositoryImpl
import com.kyawhut.atsycast.zcm.ui.source.VideoSourceRepository
import com.kyawhut.atsycast.zcm.ui.source.VideoSourceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(repository: HomeRepositoryImpl): HomeRepository = repository

    @Provides
    @Singleton
    fun provideMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository = repository

    @Provides
    @Singleton
    fun provideDetailRepository(repository: DetailRepositoryImpl): DetailRepository = repository

    @Provides
    @Singleton
    fun provideCacheRepository(repository: CacheRepositoryImpl): CacheRepository = repository

    @Provides
    @Singleton
    fun provideVideoSourceRepository(repository: VideoSourceRepositoryImpl): VideoSourceRepository =
        repository

    @Provides
    @Singleton
    fun providePlayerRepository(repository: PlayerRepositoryImpl): PlayerRepository = repository

    @Provides
    @Singleton
    fun provideSearchRepository(repository: SearchRepositoryImpl): SearchRepository = repository
}
