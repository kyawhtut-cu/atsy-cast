package com.kyawhut.atsycast.share.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.leanback.widget.BaseCardView
import androidx.viewbinding.ViewBinding

/**
 * @author kyawhtut
 * @date 8/31/21
 */
abstract class BaseCardView<VB : ViewBinding> @JvmOverloads constructor(
    layoutID: Int = -1,
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseCardView(context, attrs, defStyleAttr) {

    protected lateinit var vb: VB

    init {
        if (layoutID != -1) {
            vb = DataBindingUtil.inflate(LayoutInflater.from(context), layoutID, this, true)
            isFocusable = true
        }
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) onFocus() else onUnFocus()
    }

    abstract fun bindUI(data: Any)

    open fun onFocus() {}

    open fun onUnFocus() {}
}
