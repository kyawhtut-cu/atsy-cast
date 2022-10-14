package com.kyawhtut.atsycast.telegram.ui.auth.otp

import androidx.fragment.app.viewModels
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.base.BaseFragment
import com.kyawhtut.atsycast.telegram.databinding.AuthPageOtpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class OTPPage : BaseFragment<AuthPageOtpBinding>(R.layout.auth_page_otp) {

    companion object {
        fun pageOTP(): OTPPage {
            return OTPPage()
        }
    }

    private val vm: OTPViewModel by viewModels()

    override fun onViewCreated(vb: AuthPageOtpBinding) {
        vb.vm = vm
    }
}
