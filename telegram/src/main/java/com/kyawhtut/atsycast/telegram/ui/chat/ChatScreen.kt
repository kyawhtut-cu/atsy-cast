package com.kyawhtut.atsycast.telegram.ui.chat

import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhut.atsycast.share.base.BaseGridSupportFragment
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
        get() = 4
    override val rowsAdapter: ArrayObjectAdapter
        get() = TODO("Not yet implemented")

    override fun onItemClicked(it: Any) {
        TODO("Not yet implemented")
    }

    override fun onItemFocus(it: Any) {
        TODO("Not yet implemented")
    }

    override fun onClickRetry() {
        TODO("Not yet implemented")
    }
}
