package com.kyawhut.atsycast.msubpc.ui.card

import com.kyawhut.atsycast.share.base.BaseCardType

/**
 * @author kyawhtut
 * @date 9/6/21
 */
internal sealed class CardType : BaseCardType() {
    object MOVIES : CardType()
    object SERIES : CardType()
    object EPISODE : CardType()
    object RECENTLY_WATCH : CardType()
    object FOOTBALL : CardType()
}
