package com.kyawhtut.atsycast.telegram.ui.auth.otp

import androidx.fragment.app.viewModels
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.base.BaseFragment
import com.kyawhtut.atsycast.telegram.databinding.AuthPageOtpBinding
import com.kyawhut.atsycast.share.utils.extension.putArg
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class OTPPage : BaseFragment<AuthPageOtpBinding>(R.layout.auth_page_otp) {

    companion object {
        fun pageOTP(phone: String): OTPPage {
            return OTPPage().putArg(
                OTPViewModel.EXTRA_PHONE_NUMBER to phone
            )
        }
    }

    private val vm: OTPViewModel by viewModels()

    override fun onViewCreated(vb: AuthPageOtpBinding) {
        vb.vm = vm
    }
}
