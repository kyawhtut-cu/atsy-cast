package com.kyawhut.atsycast.ui.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.kyawhut.atsycast.databinding.DialogPasswordBinding
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.utils.extension.putArg

/**
 * @author kyawhtut
 * @date 9/16/21
 */
class DialogPassword(
    private val callback: (Boolean) -> Unit
) : DialogFragment(), View.OnClickListener {

    companion object {
        private const val extraDialogTitle = "extraDialogTitle"

        fun FragmentManager.showPasswordDialog(title: String, callback: (Boolean) -> Unit) {
            DialogPassword(callback).putArg(
                extraDialogTitle to title
            ).show(this, DialogPassword::class.simpleName)
        }

        fun FragmentActivity.showPasswordDialog(title: String, callback: (Boolean) -> Unit) {
            supportFragmentManager.showPasswordDialog(title, callback)
        }

        fun Fragment.showPasswordDialog(title: String, callback: (Boolean) -> Unit) {
            childFragmentManager.showPasswordDialog(title, callback)
        }
    }

    private lateinit var vb: DialogPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = DialogPasswordBinding.inflate(inflater, container, true)
        vb.apply {
            errorMessage = ""
            dialogTitle = arguments?.getString(extraDialogTitle, "") ?: ""
            onClickListener = this@DialogPassword
            executePendingBindings()
        }
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onClick(v: View) {
        val tag = v.tag.toString()
        if (tag == "ok") {
            return
        }
        vb.apply {
            numberPassword = if (tag == "x") "" else "%s%s".format(numberPassword ?: "", tag)
            executePendingBindings()
        }
    }
}
