package com.kyawhut.atsycast.ets2mm.data.di

import com.kyawhut.atsycast.ets2mm.ui.cache.CacheRepository
import com.kyawhut.atsycast.ets2mm.ui.cache.CacheRepositoryImpl
import com.kyawhut.atsycast.ets2mm.ui.detail.DetailRepository
import com.kyawhut.atsycast.ets2mm.ui.detail.DetailRepositoryImpl
import com.kyawhut.atsycast.ets2mm.ui.home.HomeRepository
import com.kyawhut.atsycast.ets2mm.ui.home.HomeRepositoryImpl
import com.kyawhut.atsycast.ets2mm.ui.movies.MoviesRepository
import com.kyawhut.atsycast.ets2mm.ui.movies.MoviesRepositoryImpl
import com.kyawhut.atsycast.ets2mm.ui.player.PlayerRepository
import com.kyawhut.atsycast.ets2mm.ui.player.PlayerRepositoryImpl
import com.kyawhut.atsycast.ets2mm.ui.search.SearchRepository
import com.kyawhut.atsycast.ets2mm.ui.search.SearchRepositoryImpl
import com.kyawhut.atsycast.ets2mm.ui.source.VideoSourceRepository
import com.kyawhut.atsycast.ets2mm.ui.source.VideoSourceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/15/21
 */
@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(repository: HomeRepositoryImpl): HomeRepository = repository

    @Provides
    @Singleton
    fun provideCacheRepository(repository: CacheRepositoryImpl): CacheRepository = repository

    @Provides
    @Singleton
    fun provideMovieRepository(repository: MoviesRepositoryImpl): MoviesRepository = repository

    @Provides
    @Singleton
    fun provideDetailRepository(repository: DetailRepositoryImpl): DetailRepository = repository

    @Provides
    @Singleton
    fun provideSourceRepository(repository: VideoSourceRepositoryImpl): VideoSourceRepository =
        repository

    @Provides
    @Singleton
    fun providePlayerRepository(repository: PlayerRepositoryImpl): PlayerRepository = repository

    @Provides
    @Singleton
    fun provideSearchRepository(repository: SearchRepositoryImpl): SearchRepository = repository
}
