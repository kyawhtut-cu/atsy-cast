package com.kyawhut.atsycast.free2air.data.di

import com.kyawhut.atsycast.free2air.ui.home.HomeRepository
import com.kyawhut.atsycast.free2air.ui.home.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Provides
    fun provideHomeRepository(repository: HomeRepositoryImpl): HomeRepository = repository
}
