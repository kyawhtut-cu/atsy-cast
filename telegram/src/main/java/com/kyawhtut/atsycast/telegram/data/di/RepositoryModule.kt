package com.kyawhtut.atsycast.telegram.data.di

import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.data.common.AuthRepositoryImpl
import com.kyawhtut.atsycast.telegram.ui.chat.ChatRepository
import com.kyawhtut.atsycast.telegram.ui.chat.ChatRepositoryImpl
import com.kyawhtut.atsycast.telegram.ui.chatdetail.ChatDetailRepo
import com.kyawhtut.atsycast.telegram.ui.chatdetail.ChatDetailRepoImpl
import com.kyawhtut.atsycast.telegram.ui.player.PlayerRepo
import com.kyawhtut.atsycast.telegram.ui.player.PlayerRepoImpl
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

    @Provides
    @ViewModelScoped
    fun provideChatRepository(chatRepository: ChatRepositoryImpl): ChatRepository {
        return chatRepository
    }

    @Provides
    @ViewModelScoped
    fun provideChatDetailRepository(chatDetailRepo: ChatDetailRepoImpl): ChatDetailRepo {
        return chatDetailRepo
    }

    @Provides
    @ViewModelScoped
    fun providePlayerRepo(playerRepo: PlayerRepoImpl): PlayerRepo {
        return playerRepo
    }
}
