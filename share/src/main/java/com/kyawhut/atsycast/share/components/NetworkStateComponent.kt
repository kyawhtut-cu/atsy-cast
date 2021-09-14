package com.kyawhut.atsycast.share.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.kyawhut.atsycast.share.databinding.ComponentNetworkStateBinding
import com.kyawhut.atsycast.share.utils.extension.ViewExtension.isTv

/**
 * @author kyawhtut
 * @date 9/1/21
 */
class NetworkStateComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {

    var onClickListener: () -> Unit = {}
    var message: String = ""
        set(value) {
            field = value
            vb.networkState = value
            show()
        }
    var actionText: String = ""
        set(value) {
            field = value
            vb.btnText = value
        }

    private val vb: ComponentNetworkStateBinding by lazy {
        ComponentNetworkStateBinding.inflate(LayoutInflater.from(context))
    }

    init {
        addView(vb.root)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        vb.onClickListener = this
        hide()
    }

    override fun onClick(v: View?) {
        hide()
        onClickListener()
    }

    fun hide() {
        visibility = View.GONE
        vb.btnOnClick.isTv(false)
    }

    fun requestBtnAction() {
        vb.btnOnClick.requestFocus()
    }

    private fun show() {
        visibility = View.VISIBLE
        vb.btnOnClick.apply {
            isTv(true)
            requestFocus()
        }
    }
}
