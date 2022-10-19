package com.kyawhtut.atsycast.telegram.ui.chatdetail

import androidx.lifecycle.SavedStateHandle
import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.data.model.ChatModel
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
        const val EXTRA_CHAT_DATA = "EXTRA_CHAT_DATA"
    }

    private val chatData: ChatModel by lazy {
        savedStateHandle[EXTRA_CHAT_DATA]!!
    }
    private var lastMessageID: Long? = null
    private var isHasMoreData: Boolean = true

    private var onMessageList: ((List<MessageType>) -> Unit)? = null

    var isFirstPage: Boolean = true

    fun getMessage() {
        if (chatData.chatLastMessage == null || !isHasMoreData || isLoading) return
        viewModelScope {
            isLoading = true

            chatDetailRepo.getChatHistory(
                chatData.chatID,
                lastMessageID ?: chatData.chatLastMessage?.messageID!!,
                30
            ).success {
                processOnMain {
                    if (isFirstPage) onMessageList?.invoke((listOf(chatData.chatLastMessage!!) + it))
                    else onMessageList?.invoke(it)
                }

                isHasMoreData = false

                if (it.isNotEmpty()) {
                    isHasMoreData = true
                    lastMessageID = it.last().messageID
                }
            }.done {
                isLoading = false
            }.error {
                error = it
            }
        }
    }

    fun setOnMessageListListener(listener: ((List<MessageType>) -> Unit)? = null) {
        onMessageList = listener
    }
}
