package com.kyawhtut.atsycast.telegram.ui.chat

import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import com.kyawhtut.atsycast.telegram.data.model.ChatModel
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import com.kyawhtut.atsycast.telegram.utils.done
import com.kyawhtut.atsycast.telegram.utils.error
import com.kyawhtut.atsycast.telegram.utils.success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by ios_dev - 19/10/22
 */
@HiltViewModel
internal class ChatViewModel @Inject constructor(
    private val repo: ChatRepository,
    networkState: NetworkState,
) : BaseViewModel(networkState = networkState) {

    private var onChatList: ((List<ChatModel>) -> Unit)? = null

    fun getChatList() {

        viewModelScope {
            isLoading = true

            /*delay(2000)

            processOnMain {
                onChatList?.invoke(
                    (0..20).map {
                        ChatModel(
                            it.toLong(),
                            "$it - Index",
                            "",
                            null
                        )
                    }
                )
            }

            isLoading = false*/
            repo.getChatList().success {
                processOnMain { onChatList?.invoke(it) }
            }.error {
                error = it
            }.done {
                isLoading = false
            }
        }
    }

    fun setOnChatListListener(listener: ((List<ChatModel>) -> Unit)? = null) {
        onChatList = listener
    }
}
