package com.kyawhut.atsycast.ets2mm.ui.card

import com.kyawhut.atsycast.share.base.BaseCardType

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal sealed class CardType : BaseCardType() {
    object VIDEOS : CardType()
    object EPISODE : CardType()
    object RECENTLY_WATCH : CardType()
}
