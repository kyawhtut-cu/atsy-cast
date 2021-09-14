package com.kyawhut.atsycast.data.di

import com.kyawhut.atsycast.ui.home.HomeRepository
import com.kyawhut.atsycast.ui.home.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideHomeRepository(repo: HomeRepositoryImpl): HomeRepository = repo
}
