package com.kyawhut.atsycast.eporner.data.di

import com.kyawhut.atsycast.eporner.ui.search.SearchRepository
import com.kyawhut.atsycast.eporner.ui.search.SearchRepositoryImpl
import com.kyawhut.atsycast.eporner.ui.video.VideoRepository
import com.kyawhut.atsycast.eporner.ui.video.VideoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/14/21
 */
@InstallIn(SingletonComponent::class)
@Module
internal object RepositoryModule {

    @Provides
    @Singleton
    fun provideVideoRepository(repository: VideoRepositoryImpl): VideoRepository = repository

    @Provides
    @Singleton
    fun provideSearchRepository(repository: SearchRepositoryImpl): SearchRepository = repository
}
