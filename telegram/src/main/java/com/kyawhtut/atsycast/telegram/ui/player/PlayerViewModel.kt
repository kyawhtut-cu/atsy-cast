package com.kyawhtut.atsycast.telegram.ui.player

import androidx.lifecycle.SavedStateHandle
import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.data.model.PlayerModel
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import com.kyawhtut.atsycast.telegram.utils.done
import com.kyawhtut.atsycast.telegram.utils.error
import com.kyawhtut.atsycast.telegram.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 21/10/2022
 */
@HiltViewModel
internal class PlayerViewModel @Inject constructor(
    networkState: NetworkState,
    authRepository: AuthRepository,
    private val repo: PlayerRepo,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel(networkState, authRepository) {

    companion object {
        const val EXTRA_CHAT_ID = "EXTRA_CHAT_ID"
        const val EXTRA_MESSAGE_ID = "EXTRA_MESSAGE_ID"
    }

    private val chatID: Long by lazy {
        savedStateHandle[EXTRA_CHAT_ID] ?: 0L
    }
    private val messageID: Long by lazy {
        savedStateHandle[EXTRA_MESSAGE_ID] ?: 0L
    }

    private var onVideoPath: ((PlayerModel) -> Unit)? = null

    init {
        viewModelScope {
            isLoading = true
            repo.getVideo(chatID, messageID).success {
                processOnMain { onVideoPath?.invoke(it) }
            }.done {
                processOnMain { isLoading = false }
            }.error {
                error = it
            }
        }
    }

    fun setOnVideoPathListener(listener: ((PlayerModel) -> Unit)? = null) {
        onVideoPath = listener
    }
}
