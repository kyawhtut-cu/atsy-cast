package com.kyawhut.atsycast.data.di

import com.kyawhut.atsycast.ui.home.HomeRepository
import com.kyawhut.atsycast.ui.home.HomeRepositoryImpl
import com.kyawhut.atsycast.ui.password.PasswordRepository
import com.kyawhut.atsycast.ui.password.PasswordRepositoryImpl
import com.kyawhut.atsycast.ui.setting.SettingRepository
import com.kyawhut.atsycast.ui.setting.SettingRepositoryImpl
import com.kyawhut.atsycast.ui.splash.SplashRepository
import com.kyawhut.atsycast.ui.splash.SplashRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHomeRepository(repo: HomeRepositoryImpl): HomeRepository = repo

    @Provides
    @Singleton
    fun provideSettingRepository(repo: SettingRepositoryImpl): SettingRepository = repo

    @Provides
    @Singleton
    fun providePasswordRepository(repo: PasswordRepositoryImpl): PasswordRepository = repo

    @Provides
    @Singleton
    fun provideSplashRepository(repo: SplashRepositoryImpl): SplashRepository = repo
}
