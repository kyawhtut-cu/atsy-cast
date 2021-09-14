package com.kyawhut.atsycast.share.ui.card

import com.kyawhut.atsycast.share.base.BaseCardType

/**
 * @author kyawhtut
 * @date 9/7/21
 */
sealed class DetailCardType : BaseCardType() {
    object ACTION : DetailCardType()
}
