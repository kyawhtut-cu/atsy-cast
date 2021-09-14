package com.kyawhut.atsycast.share.utils.focus

import android.view.View

/**
 * @author kyawhtut
 * @date 9/3/21
 */
interface FocusHighlight {

    fun onItemFocused(view: View, hasFocus: Boolean)

    fun onInitializeView(view: View)

}
