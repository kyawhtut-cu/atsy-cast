package com.kyawhut.atsycast.share.ui.error

import android.app.Activity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.leanback.app.ErrorSupportFragment
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.utils.extension.putArg
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 31/03/2020
 */
class ErrorFragment : ErrorSupportFragment() {

    companion object {

        const val TAG = "ErrorFragment"
        private const val extraBackEnabled = "extra.backEnabled"
        const val extraErrorMessage: String = "extra.errorMessage"

        fun newInstance(isBackEnable: Boolean = false) = ErrorFragment().putArg(
            extraBackEnabled to isBackEnable
        )
    }

    var isBackEnabled: Boolean = false
    var errorMessage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.lb_ic_sad_cloud)
        message = if(errorMessage.isEmpty()) resources.getString(R.string.lbl_error_fragment_message)
        else errorMessage
        setDefaultBackground(true)

        buttonText = resources.getString(R.string.lbl_error_fragment_button)
        setButtonClickListener {
            targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isBackEnabled) {
                        Timber.d("onBackPressed.")
                        targetFragment?.onActivityResult(
                            targetRequestCode,
                            Activity.RESULT_CANCELED,
                            null
                        )
                    }
                }
            }
        )
    }
}
