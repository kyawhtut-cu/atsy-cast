package com.kyawhtut.atsycast.telegram.ui.auth.password

import androidx.fragment.app.viewModels
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.base.BaseFragment
import com.kyawhtut.atsycast.telegram.databinding.AuthPagePasswordBinding
import com.kyawhut.atsycast.share.utils.extension.putArg
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class PasswordPage : BaseFragment<AuthPagePasswordBinding>(R.layout.auth_page_password) {

    companion object {
        fun pagePassword(hint: String): PasswordPage {
            return PasswordPage().putArg(
                PasswordViewModel.EXTRA_PASSWORD_HINT to hint
            )
        }
    }

    private val vm: PasswordViewModel by viewModels()

    override fun onViewCreated(vb: AuthPagePasswordBinding) {
        vb.vm = vm
    }
}
