package com.kyawhtut.atsycast.telegram.ui.chatdetail

import androidx.lifecycle.SavedStateHandle
import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.data.model.MessageType
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import com.kyawhtut.atsycast.telegram.utils.done
import com.kyawhtut.atsycast.telegram.utils.error
import com.kyawhtut.atsycast.telegram.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
@HiltViewModel
internal class ChatDetailViewModel @Inject constructor(
    authRepository: AuthRepository,
    networkState: NetworkState,
    private val chatDetailRepo: ChatDetailRepo,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel(networkState, authRepository) {

    companion object {
        const val EXTRA_CHAT_ID = "EXTRA_CHAT_ID"
    }

    private val chatID: Long by lazy {
        savedStateHandle[EXTRA_CHAT_ID] ?: 0L
    }
    private var chatLastMessage: MessageType? = null
    private var isHasMoreData: Boolean = true

    private var onMessageList: ((List<MessageType>, Boolean) -> Unit)? = null

    fun getChatMessage(callback: ((String) -> Unit)? = null) {
        if (chatID == 0L) return
        viewModelScope {
            isLoading = true

            chatDetailRepo.getChatDetail(chatID).success {
                processOnMain { callback?.invoke(it.chatModel.chatTitle) }

                chatLastMessage = it.chatLastMessage
                isHasMoreData = true

                getMessage(false)
            }.error {
                error = it
            }
        }
    }

    fun getMessage(isNextMessage: Boolean) {
        if (isNextMessage && isLoading) return
        if (chatLastMessage == null || !isHasMoreData) return
        viewModelScope {
            if (isNextMessage) isLoading = true

            chatDetailRepo.getChatHistory(
                chatID,
                chatLastMessage!!.messageID,
                30
            ).success {
                processOnMain {
                    if (isNextMessage) onMessageList?.invoke(it, true)
                    else onMessageList?.invoke(
                        listOf(chatLastMessage!!) + it,
                        false
                    )
                }

                isHasMoreData = false

                if (it.isNotEmpty()) {
                    isHasMoreData = true
                    chatLastMessage = it.last()
                }
            }.done {
                isLoading = false
            }.error {
                error = it
            }
        }
    }

    fun setOnMessageListListener(listener: ((List<MessageType>, Boolean) -> Unit)? = null) {
        onMessageList = listener
    }
}
