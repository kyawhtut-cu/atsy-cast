package com.kyawhut.atsycast.gs_mm.ui.card

import com.kyawhut.atsycast.share.base.BaseCardType

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal sealed class CardType : BaseCardType() {
    object VIDEO : CardType()
    object EPISODE : CardType()
    object PLAYLIST : CardType()
    object RECENTLY_WATCH : CardType()
}
