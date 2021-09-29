package com.kyawhut.astycast.gsmovie.ui.card

import com.kyawhut.atsycast.share.base.BaseCardType

/**
 * @author kyawhtut
 * @date 9/29/21
 */
internal sealed class CardType : BaseCardType() {
    object VIDEO : CardType()
    object EPISODE : CardType()
}
