package com.kyawhut.astycast.gsmovie.data.di

import com.kyawhut.astycast.gsmovie.ui.detail.DetailRepository
import com.kyawhut.astycast.gsmovie.ui.detail.DetailRepositoryImpl
import com.kyawhut.astycast.gsmovie.ui.home.HomeRepository
import com.kyawhut.astycast.gsmovie.ui.home.HomeRepositoryImpl
import com.kyawhut.astycast.gsmovie.ui.player.PlayerRepository
import com.kyawhut.astycast.gsmovie.ui.player.PlayerRepositoryImpl
import com.kyawhut.astycast.gsmovie.ui.source.VideoSourceRepository
import com.kyawhut.astycast.gsmovie.ui.source.VideoSourceRepositoryImpl
import com.kyawhut.astycast.gsmovie.ui.video.VideoRepository
import com.kyawhut.astycast.gsmovie.ui.video.VideoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/29/21
 */
@InstallIn(SingletonComponent::class)
@Module
internal object RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(repository: HomeRepositoryImpl): HomeRepository = repository

    @Provides
    @Singleton
    fun provideVideoRepository(repository: VideoRepositoryImpl): VideoRepository = repository

    @Provides
    @Singleton
    fun provideDetailRepository(repository: DetailRepositoryImpl): DetailRepository = repository

    @Provides
    @Singleton
    fun provideVideoSourceRepository(repository: VideoSourceRepositoryImpl): VideoSourceRepository =
        repository

    @Provides
    @Singleton
    fun providePlayerRepository(repository: PlayerRepositoryImpl): PlayerRepository = repository
}
