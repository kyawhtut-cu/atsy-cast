package com.kyawhut.atsycast.msubpc.data.di

import com.kyawhut.atsycast.msubpc.ui.adult.AdultRepository
import com.kyawhut.atsycast.msubpc.ui.adult.AdultRepositoryImpl
import com.kyawhut.atsycast.msubpc.ui.cache.CacheRepository
import com.kyawhut.atsycast.msubpc.ui.cache.CacheRepositoryImpl
import com.kyawhut.atsycast.msubpc.ui.detail.DetailRepository
import com.kyawhut.atsycast.msubpc.ui.detail.DetailRepositoryImpl
import com.kyawhut.atsycast.msubpc.ui.football.FootballRepository
import com.kyawhut.atsycast.msubpc.ui.football.FootballRepositoryImpl
import com.kyawhut.atsycast.msubpc.ui.home.HomeRepository
import com.kyawhut.atsycast.msubpc.ui.home.HomeRepositoryImpl
import com.kyawhut.atsycast.msubpc.ui.movies.MoviesRepository
import com.kyawhut.atsycast.msubpc.ui.movies.MoviesRepositoryImpl
import com.kyawhut.atsycast.msubpc.ui.player.PlayerRepository
import com.kyawhut.atsycast.msubpc.ui.player.PlayerRepositoryImpl
import com.kyawhut.atsycast.msubpc.ui.search.SearchRepository
import com.kyawhut.atsycast.msubpc.ui.search.SearchRepositoryImpl
import com.kyawhut.atsycast.msubpc.ui.series.SeriesRepository
import com.kyawhut.atsycast.msubpc.ui.series.SeriesRepositoryImpl
import com.kyawhut.atsycast.msubpc.ui.source.VideoSourceRepository
import com.kyawhut.atsycast.msubpc.ui.source.VideoSourceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/6/21
 */
@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(repository: MoviesRepositoryImpl): MoviesRepository = repository

    @Provides
    @Singleton
    fun provideSeriesRepository(repository: SeriesRepositoryImpl): SeriesRepository = repository

    @Provides
    @Singleton
    fun provideSearchRepository(repository: SearchRepositoryImpl): SearchRepository = repository

    @Provides
    fun provideDetailRepository(repository: DetailRepositoryImpl): DetailRepository = repository

    @Provides
    fun provideVideoSourceRepository(repository: VideoSourceRepositoryImpl): VideoSourceRepository =
        repository

    @Provides
    fun providePlayerRepository(repository: PlayerRepositoryImpl): PlayerRepository = repository

    @Provides
    @Singleton
    fun provideCacheRepository(repository: CacheRepositoryImpl): CacheRepository = repository

    @Provides
    @Singleton
    fun provideHomeRepository(repository: HomeRepositoryImpl): HomeRepository = repository

    @Provides
    @Singleton
    fun provideFootballRepository(repository: FootballRepositoryImpl): FootballRepository =
        repository

    @Provides
    @Singleton
    fun provideAdultRepository(repository: AdultRepositoryImpl): AdultRepository = repository
}