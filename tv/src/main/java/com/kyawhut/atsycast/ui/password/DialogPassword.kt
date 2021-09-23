package com.kyawhut.atsycast.ui.password

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.databinding.DialogPasswordBinding
import com.kyawhut.atsycast.share.utils.extension.putArg
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/16/21
 */
@AndroidEntryPoint
class DialogPassword(
    private val callback: (Boolean) -> Unit
) : DialogFragment(), View.OnClickListener {

    companion object {
        private const val extraPasswordType = "extraPasswordType"

        enum class PasswordType {
            ADULT, DEVICE
        }

        fun FragmentManager.showPasswordDialog(type: PasswordType, callback: (Boolean) -> Unit) {
            DialogPassword(callback).putArg(
                extraPasswordType to type
            ).show(this, DialogPassword::class.simpleName)
        }

        fun FragmentActivity.showPasswordDialog(type: PasswordType, callback: (Boolean) -> Unit) {
            supportFragmentManager.showPasswordDialog(type, callback)
        }

        fun Fragment.showPasswordDialog(type: PasswordType, callback: (Boolean) -> Unit) {
            childFragmentManager.showPasswordDialog(type, callback)
        }
    }

    private val vm: PasswordViewModel by viewModels()

    private lateinit var vb: DialogPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    private val type: PasswordType by lazy {
        arguments?.get(extraPasswordType) as PasswordType
    }
    var isCorrect: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = DialogPasswordBinding.inflate(inflater, container, true)
        vb.apply {
            isLoading = false
            errorMessage = ""
            dialogTitle = when (type) {
                PasswordType.ADULT -> getString(R.string.lbl_dark_side_dialog_password_title)
                PasswordType.DEVICE -> getString(R.string.lbl_device_dialog_password_title)
            }
            onClickListener = this@DialogPassword
            executePendingBindings()
        }
        return vb.root
    }

    override fun onClick(v: View) {
        val tag = v.tag.toString()
        if (tag == "ok") {
            isCancelable = false
            toggleLoading(true)
            if (type == PasswordType.ADULT) {
                checkAdultPassword()
            } else if (type == PasswordType.DEVICE) {
                checkDevicePassword()
            }
            return
        }
        vb.apply {
            errorMessage = ""
            numberPassword = if (tag == "x") "" else "%s%s".format(numberPassword ?: "", tag)
            executePendingBindings()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        callback(isCorrect)
        super.onDismiss(dialog)
    }

    private fun toggleLoading(isLoading: Boolean) {
        vb.apply {
            this.isLoading = isLoading
            iosLoading.toggleAnimation(isLoading)
            executePendingBindings()
        }
    }

    private fun checkAdultPassword() {
        vm.checkAdultPassword(requireContext(), vb.numberPassword ?: "") { status, message ->
            toggleLoading(false)
            isCancelable = true
            if (status) {
                isCorrect = true
                dismissAllowingStateLoss()
            } else {
                vb.apply {
                    numberPassword = ""
                    errorMessage = message
                    executePendingBindings()
                }
            }
        }
    }

    private fun checkDevicePassword() {
        vm.checkDevicePassword(requireContext(), vb.numberPassword ?: "") { status, message ->
            toggleLoading(false)
            isCancelable = true
            if (status) {
                isCorrect = true
                dismissAllowingStateLoss()
            } else {
                vb.apply {
                    numberPassword = ""
                    errorMessage = message
                    executePendingBindings()
                }
            }
        }
    }
}
