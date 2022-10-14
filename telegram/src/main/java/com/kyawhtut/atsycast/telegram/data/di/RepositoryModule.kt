package com.kyawhtut.atsycast.telegram.data.di

import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.data.common.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by Kyaw Htut on 13/10/2022.
 */
@InstallIn(ViewModelComponent::class)
@Module
internal object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository {
        return authRepository
    }
}
