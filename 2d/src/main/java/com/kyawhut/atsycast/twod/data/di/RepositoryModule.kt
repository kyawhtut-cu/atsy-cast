package com.kyawhut.atsycast.twod.data.di

import com.kyawhut.atsycast.twod.ui.home.HomeRepository
import com.kyawhut.atsycast.twod.ui.home.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 9/23/21
 */
@InstallIn(SingletonComponent::class)
@Module
internal object RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(repo: HomeRepositoryImpl): HomeRepository = repo
}
