package com.kyawhtut.atsycast.telegram.ui.card

import com.kyawhut.atsycast.share.base.BaseCardType

/**
 * @author kyawhtut
 * @date 19/10/2022
 */
internal sealed class CardType : BaseCardType() {
    object Chat : CardType()
    object Message : CardType()
}
