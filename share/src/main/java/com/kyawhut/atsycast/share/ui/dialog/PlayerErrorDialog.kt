package com.kyawhut.atsycast.share.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.databinding.PlayerErrorViewBinding
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import com.kyawhut.atsycast.share.utils.extension.putArg

/**
 * @author kyawhtut
 * @date 9/10/21
 */
class PlayerErrorDialog(private val callback: () -> Unit) : DialogFragment() {

    companion object {
        private const val ERROR_MESSAGE = "ERROR_MESSAGE"
        private const val ERROR_FULL_MESSAGE = "ERROR_FULL_MESSAGE"
        private const val ERROR_PARSE_MODE = "ERROR_PARSE_MODE"

        fun Fragment.showPlayerError(
            message: String,
            fullErrorMessage: String,
            parseMode: String = "html",
            callback: () -> Unit
        ) {
            childFragmentManager.showPlayerError(message, fullErrorMessage, parseMode, callback)
        }

        fun FragmentActivity.showPlayerError(
            message: String,
            fullErrorMessage: String = "",
            parseMode: String = "html",
            callback: () -> Unit
        ) {
            supportFragmentManager.showPlayerError(message, fullErrorMessage, parseMode, callback)
        }

        fun FragmentManager.showPlayerError(
            message: String,
            fullErrorMessage: String,
            parseMode: String = "html",
            callback: () -> Unit
        ) {
            PlayerErrorDialog(callback).putArg(
                ERROR_MESSAGE to "ကြည့်ရှုရာတွင် ပြဿနာ တစ်စုံတစ်ရာ ရှိနေပါသည်။\n%s".format(message),
                ERROR_FULL_MESSAGE to fullErrorMessage,
                ERROR_PARSE_MODE to parseMode,
            ).show(this, PlayerErrorDialog::class.java.name)
        }

        fun FragmentManager.showError(
            message: String,
            fullErrorMessage: String,
            parseMode: String = "html",
            callback: () -> Unit
        ) {
            PlayerErrorDialog(callback).putArg(
                ERROR_MESSAGE to message,
                ERROR_FULL_MESSAGE to fullErrorMessage,
                ERROR_PARSE_MODE to parseMode,
            ).show(this, PlayerErrorDialog::class.java.name)
        }

        fun FragmentActivity.showError(
            message: String,
            fullErrorMessage: String = "",
            parseMode: String = "html",
            callback: () -> Unit
        ) {
            supportFragmentManager.showError(message, fullErrorMessage, parseMode, callback)
        }
    }

    private lateinit var vb: PlayerErrorViewBinding
    private val message: String by lazy {
        arguments?.getString(ERROR_MESSAGE) ?: ""
    }
    private val errorFullMessage: String by lazy {
        arguments?.getString(ERROR_FULL_MESSAGE) ?: ""
    }
    private val parseMode: String by lazy {
        arguments?.getString(ERROR_PARSE_MODE) ?: "html"
    }

    override fun isCancelable(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
        if (errorFullMessage.isNotEmpty()) TelegramHelper.sendLog(errorFullMessage, parseMode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = PlayerErrorViewBinding.inflate(inflater, container, true)
        vb.apply {
            errorMessage = message
            actionText = "ပြန်လည်ကြိုးစားမည်"
            executePendingBindings()
        }
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb.errorAnim.playAnimation()

        vb.btnAction.setOnClickListener {
            dismissAllowingStateLoss()
            callback()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                requireActivity().finishAndRemoveTask()
            }
        }
    }
}
