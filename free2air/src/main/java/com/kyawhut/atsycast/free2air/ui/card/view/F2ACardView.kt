package com.kyawhut.atsycast.free2air.ui.card.view

import android.content.Context
import com.kyawhut.atsycast.free2air.R
import com.kyawhut.atsycast.free2air.data.network.response.Free2AirResponse
import com.kyawhut.atsycast.free2air.databinding.CardChannelBinding
import com.kyawhut.atsycast.share.base.BaseCardView

/**
 * @author kyawhtut
 * @date 9/1/21
 */
class F2ACardView(context: Context) : BaseCardView<CardChannelBinding>(
    R.layout.card_channel, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        val channel = data as Free2AirResponse
        vb.apply {
            isPlaying = channel.isPlaying
            channelLogo = channel.channelCover
            channelName = channel.channelName
        }
    }
}
