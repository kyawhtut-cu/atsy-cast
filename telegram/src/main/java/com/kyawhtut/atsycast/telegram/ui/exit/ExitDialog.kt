package com.kyawhtut.atsycast.telegram.ui.exit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.databinding.DialogExitPlayerBinding

/**
 * @author kyawhtut
 * @date 22/10/2022
 */
internal class ExitDialog : DialogFragment() {

    companion object {
        fun FragmentManager.showExitDialog(callback: ((Boolean) -> Unit)? = null) {
            ExitDialog().setOnDismissListener(callback).show(
                this,
                ExitDialog::class.java.name
            )
        }
    }

    private var vb: DialogExitPlayerBinding? = null
    private var onDismiss: ((Boolean) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                onDismiss?.invoke(false)
                super.onBackPressed()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = DialogExitPlayerBinding.inflate(
            inflater,
            container,
            true
        )
        vb?.lifecycleOwner = this
        return vb?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb?.btnExit?.setOnClickListener {
            onDismiss?.invoke(true)
            dismissAllowingStateLoss()
        }
        vb?.btnCancel?.setOnClickListener {
            onDismiss?.invoke(false)
            dismissAllowingStateLoss()
        }
    }

    fun setOnDismissListener(listener: ((Boolean) -> Unit)? = null): ExitDialog {
        onDismiss = listener
        return this
    }

    override fun onDestroy() {
        vb = null
        super.onDestroy()
    }
}