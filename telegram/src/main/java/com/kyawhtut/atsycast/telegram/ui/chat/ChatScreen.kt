package com.kyawhtut.atsycast.telegram.ui.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.data.model.ChatModel
import com.kyawhtut.atsycast.telegram.ui.card.CardPresenter
import com.kyawhtut.atsycast.telegram.ui.chatdetail.ChatDetailScreen.Companion.openDetail
import com.kyawhut.atsycast.share.base.BaseGridSupportFragment
import com.kyawhut.atsycast.share.components.iosloading.ToolBox
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by ios_dev - 19/10/22
 */
@AndroidEntryPoint
internal class ChatScreen : BaseGridSupportFragment<ChatViewModel>() {

    companion object {
        fun screenChat(): ChatScreen {
            return ChatScreen()
        }
    }

    override val isPagingEnable: Boolean
        get() = false
    override val vm: ChatViewModel by viewModels()
    override val numberOfColumns: Int
        get() = (ToolBox.screenWidth / resources.getDimensionPixelSize(R.dimen.x150dp)) - 1
    override val rowsAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(CardPresenter(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.setOnChatListListener {
            rowsAdapter.setItems(it, ChatModel.diffUtil)
        }

        vm.getChatList()
    }

    override fun onItemClicked(it: Any) {
        if (it is ChatModel) {
            openDetail(it.chatID)
        }
    }

    override fun onItemFocus(it: Any) {
    }

    override fun onClickRetry() {
    }
}
