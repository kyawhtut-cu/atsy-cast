package com.kyawhut.atsycast.doujin.data.di

import com.kyawhut.atsycast.doujin.ui.video.VideoRepository
import com.kyawhut.atsycast.doujin.ui.video.VideoRepositoryImpl
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
}
