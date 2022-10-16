package com.kyawhut.atsycast.share.utils.extension

import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.kyawhut.atsycast.share.utils.extension.Extension.hideKeyboard

/**
 * @author kyawhtut
 * @date 8/31/21
 */
object ViewExtension {

    @JvmStatic
    @BindingAdapter("isTv")
    fun View.isTv(isTv: Boolean?) {
        this.apply {
            if (this is ViewGroup)
                descendantFocusability = if (isTv == true) ViewGroup.FOCUS_AFTER_DESCENDANTS
                else ViewGroup.FOCUS_BLOCK_DESCENDANTS
            isFocusable = isTv == true
            isFocusableInTouchMode = isTv == true
            isEnabled = isTv == true
        }
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun View.isVisible(isVisible: Boolean?) {
        visibility = if (isVisible == true) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("isGone")
    fun View.isGone(isGone: Boolean?) {
        visibility = if (isGone == true) View.GONE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("isInvisible")
    fun View.isInvisible(isInvisible: Boolean?) {
        visibility = if (isInvisible == true) View.INVISIBLE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("isSelected")
    fun View.isSelected(isSelected: Boolean?) {
        this.isSelected = isSelected == true
    }

    @JvmStatic
    @BindingAdapter("focusListener")
    fun View.focusListener(callback: FocusCallback) {
        this.setOnFocusChangeListener { _, isFocus ->
            callback.onFocus(isFocus)
        }
    }

    @JvmStatic
    @BindingAdapter("actionDone")
    fun EditText.actionDone(view: View) {
        this.imeOptions = EditorInfo.IME_ACTION_DONE
        this.setOnEditorActionListener { _, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) {
                context hideKeyboard this
                this.clearFocus()
                view.requestFocus()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    interface FocusCallback {

        fun onFocus(isFocus: Boolean)
    }
}
