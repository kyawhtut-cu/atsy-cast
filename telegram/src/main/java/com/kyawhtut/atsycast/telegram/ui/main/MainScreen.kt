package com.kyawhtut.atsycast.telegram.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.PageRow
import androidx.leanback.widget.SectionRow
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.ui.chat.ChatScreen
import com.kyawhut.atsycast.share.base.BaseBrowseSupportFragment

internal class MainScreen : BaseBrowseSupportFragment() {

    companion object {
        fun screenMain(): MainScreen {
            return MainScreen()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addRowItem(SectionRow("Telegram"))
        resources.getStringArray(R.array.telegramDrawer).forEachIndexed { index, s ->
            addRowItem(
                PageRow(
                    HeaderItem(
                        index.toLong(), s
                    )
                )
            )
        }
    }

    override fun onClickRetry() {
    }

    override fun onCreateRowFragment(header: HeaderItem): Fragment {
        return ChatScreen.screenChat()
    }
}
