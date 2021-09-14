package com.kyawhut.atsycast.share.components

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.leanback.widget.RowHeaderView
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.utils.binding.TextViewBinding.applyMMFont

/**
 * @author kyawhtut
 * @date 9/2/21
 */
class RowHeaderComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        findViewById<RowHeaderView>(R.id.row_header).applyMMFont()
        findViewById<TextView>(R.id.row_header_description).applyMMFont()
    }
}
