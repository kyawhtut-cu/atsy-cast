package com.kyawhut.atsycast.doujin.ui.card

import com.kyawhut.atsycast.share.base.BaseCardType

/**
 * @author kyawhtut
 * @date 9/14/21
 */
internal sealed class CardType : BaseCardType() {
    object VIDEO : CardType()
}
