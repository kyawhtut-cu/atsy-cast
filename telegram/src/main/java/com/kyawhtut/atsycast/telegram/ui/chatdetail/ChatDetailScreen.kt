package com.kyawhtut.atsycast.telegram.ui.chatdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.data.model.ChatModel
import com.kyawhtut.atsycast.telegram.data.model.MessageType
import com.kyawhtut.atsycast.telegram.databinding.MainScreenBinding
import com.kyawhtut.atsycast.telegram.ui.card.CardPresenter
import com.kyawhtut.atsycast.telegram.utils.AuthState
import com.kyawhut.atsycast.share.base.BaseGridFragment
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.components.iosloading.ToolBox
import com.kyawhut.atsycast.share.utils.extension.putArg
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
@AndroidEntryPoint
internal class ChatDetailScreen : BaseTvActivity<MainScreenBinding>() {

    companion object {
        fun Fragment.openDetail(chatData: ChatModel) {
            requireActivity().startActivity<ChatDetailScreen>(
                ChatDetailViewModel.EXTRA_CHAT_DATA to chatData
            )
        }
    }

    override val layoutID: Int
        get() = R.layout.main_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            replace(
                R.id.content_frame,
                DetailScreen.screenDetail(
                    intent.getSerializableExtra(ChatDetailViewModel.EXTRA_CHAT_DATA) as ChatModel
                )
            )
        }
    }

    @AndroidEntryPoint
    class DetailScreen : BaseGridFragment<ChatDetailViewModel>() {

        companion object {
            fun screenDetail(chatData: ChatModel): DetailScreen {
                return DetailScreen().putArg(
                    ChatDetailViewModel.EXTRA_CHAT_DATA to chatData
                )
            }
        }

        override val vm: ChatDetailViewModel by viewModels()
        override val rowsAdapter: ArrayObjectAdapter by lazy {
            ArrayObjectAdapter(CardPresenter(requireContext()))
        }
        override val numberOfColumns: Int
            get() = (ToolBox.screenWidth / resources.getDimensionPixelSize(R.dimen.x215dp))

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            vm.setOnAuthStateListener {
                if (it !is AuthState.LoggedIn) requireActivity().finishAffinity()
            }
            vm.setOnMessageListListener {
                if (vm.isFirstPage) rowsAdapter.setItems(it, MessageType.diffUtil)
                else rowsAdapter.addAll(rowsAdapter.size(), it)
            }

            vm.getMessage()
        }

        override fun onItemClicked(it: Any) {
        }

        override fun onItemFocus(it: Any) {
        }

        override fun onClickRetry() {
        }

        override fun onLoadMore() {
            vm.isFirstPage = false
            vm.getMessage()
        }
    }
}
