package com.kyawhut.atsycast.gs_mm.data.di

import com.kyawhut.atsycast.gs_mm.ui.cache.CacheRepository
import com.kyawhut.atsycast.gs_mm.ui.cache.CacheRepositoryImpl
import com.kyawhut.atsycast.gs_mm.ui.detail.DetailRepository
import com.kyawhut.atsycast.gs_mm.ui.detail.DetailRepositoryImpl
import com.kyawhut.atsycast.gs_mm.ui.home.HomeRepository
import com.kyawhut.atsycast.gs_mm.ui.home.HomeRepositoryImpl
import com.kyawhut.atsycast.gs_mm.ui.player.PlayerRepository
import com.kyawhut.atsycast.gs_mm.ui.player.PlayerRepositoryImpl
import com.kyawhut.atsycast.gs_mm.ui.search.SearchRepository
import com.kyawhut.atsycast.gs_mm.ui.search.SearchRepositoryImpl
import com.kyawhut.atsycast.gs_mm.ui.source.VideoSourceRepository
import com.kyawhut.atsycast.gs_mm.ui.source.VideoSourceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.kyawhut.atsycast.gs_mm.ui.videogrid.VideoRepository as VideoGridRepo
import com.kyawhut.atsycast.gs_mm.ui.videogrid.VideoRepositoryImpl as VideoGridRepoImpl
import com.kyawhut.atsycast.gs_mm.ui.videolist.VideoRepository as VideoListRepo
import com.kyawhut.atsycast.gs_mm.ui.videolist.VideoRepositoryImpl as VideoListRepoImpl

/**
 * @author kyawhtut
 * @date 10/8/21
 */
@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepo(repository: HomeRepositoryImpl): HomeRepository = repository

    @Provides
    @Singleton
    fun provideVideoGridRepo(repository: VideoGridRepoImpl): VideoGridRepo = repository

    @Provides
    @Singleton
    fun provideVideoListRepo(repository: VideoListRepoImpl): VideoListRepo = repository

    @Provides
    @Singleton
    fun provideCacheRepo(repo: CacheRepositoryImpl): CacheRepository = repo

    @Provides
    @Singleton
    fun provideDetailRepo(repo: DetailRepositoryImpl): DetailRepository = repo

    @Provides
    @Singleton
    fun provideVideoSourceRepo(repo: VideoSourceRepositoryImpl): VideoSourceRepository = repo

    @Provides
    @Singleton
    fun providePlayerRepo(repo: PlayerRepositoryImpl): PlayerRepository = repo

    @Provides
    @Singleton
    fun provideSearchRepo(repo: SearchRepositoryImpl): SearchRepository = repo
}
