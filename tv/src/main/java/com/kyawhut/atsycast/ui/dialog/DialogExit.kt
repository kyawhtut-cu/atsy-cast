package com.kyawhut.atsycast.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.databinding.DialogExitBinding
import com.kyawhut.atsycast.share.utils.extension.Extension.convertDpToPixel

/**
 * @author kyawhtut
 * @date 9/25/21
 */
class DialogExit : DialogFragment(), View.OnClickListener {

    companion object {
        private var PARENT_WIDTH: Float = 250f

        private fun FragmentManager.showExitDialog() {
            DialogExit().show(this, DialogExit::class.simpleName)
        }

        fun FragmentActivity.showExitDialog() {
            supportFragmentManager.showExitDialog()
        }

        fun Fragment.showExitDialog() {
            childFragmentManager.showExitDialog()
        }
    }

    private lateinit var vb: DialogExitBinding

    private val countDownTimer: CountDownTimer by lazy {
        object : CountDownTimer(5000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                vb.viewAnimation.updateLayoutParams {
                    width = ((5000 - millisUntilFinished).toInt() * convertDpToPixel(PARENT_WIDTH).toInt()) / 5000
                }
            }

            override fun onFinish() {
                dismissAllowingStateLoss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = DialogExitBinding.inflate(inflater, container, true)
        vb.onClickListener = this
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countDownTimer.start()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_exit -> {
                dismissAllowingStateLoss()
                requireActivity().finishAndRemoveTask()
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        countDownTimer.cancel()
        super.onDismiss(dialog)
    }
}
