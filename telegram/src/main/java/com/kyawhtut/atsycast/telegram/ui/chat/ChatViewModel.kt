package com.kyawhtut.atsycast.telegram.ui.chat

import com.kyawhtut.atsycast.telegram.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by ios_dev - 19/10/22
 */
internal class ChatViewModel @Inject constructor(
    private val repo: ChatRepository
) : BaseViewModel() {
}
