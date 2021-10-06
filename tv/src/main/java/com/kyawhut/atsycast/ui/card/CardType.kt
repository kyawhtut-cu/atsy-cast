package com.kyawhut.atsycast.ui.card

import com.kyawhut.atsycast.share.base.BaseCardType

/**
 * @author kyawhtut
 * @date 8/31/21
 */
sealed class CardType : BaseCardType() {
    object FEATURE : CardType()
    object WATCHLATER : CardType()
    object RECENTLYWATCH : CardType()
}
