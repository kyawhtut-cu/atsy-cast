package com.kyawhut.atsycast.msys.data.di

import com.kyawhut.atsycast.msys.ui.cache.CacheRepository
import com.kyawhut.atsycast.msys.ui.cache.CacheRepositoryImpl
import com.kyawhut.atsycast.msys.ui.detail.DetailRepository
import com.kyawhut.atsycast.msys.ui.detail.DetailRepositoryImpl
import com.kyawhut.atsycast.msys.ui.genres.MoviesRepository
import com.kyawhut.atsycast.msys.ui.genres.MoviesRepositoryImpl
import com.kyawhut.atsycast.msys.ui.home.HomeRepository
import com.kyawhut.atsycast.msys.ui.home.HomeRepositoryImpl
import com.kyawhut.atsycast.msys.ui.player.PlayerRepository
import com.kyawhut.atsycast.msys.ui.player.PlayerRepositoryImpl
import com.kyawhut.atsycast.msys.ui.search.SearchRepository
import com.kyawhut.atsycast.msys.ui.search.SearchRepositoryImpl
import com.kyawhut.atsycast.msys.ui.source.VideoSourceRepository
import com.kyawhut.atsycast.msys.ui.source.VideoSourceRepositoryImpl
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
