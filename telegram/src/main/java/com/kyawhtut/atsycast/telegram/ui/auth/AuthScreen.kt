package com.kyawhtut.atsycast.telegram.ui.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.base.BaseFragment
import com.kyawhtut.atsycast.telegram.databinding.AuthScreenBinding
import com.kyawhtut.atsycast.telegram.ui.auth.otp.OTPPage
import com.kyawhtut.atsycast.telegram.ui.auth.password.PasswordPage
import com.kyawhtut.atsycast.telegram.ui.auth.phone.PhonePage
import com.kyawhtut.atsycast.telegram.ui.auth.qr.QRPage
import com.kyawhtut.atsycast.telegram.utils.AuthState
import com.kyawhut.atsycast.share.utils.extension.putArg
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
internal class AuthScreen : BaseFragment<AuthScreenBinding>(R.layout.auth_screen) {

    companion object {
        fun screenAuth(authState: AuthState): AuthScreen {
            return AuthScreen().putArg(
                AuthViewModel.EXTRA_AUTH_STATE to authState
            )
        }
    }

    private val vm: AuthViewModel by viewModels()

    override fun onViewCreated(vb: AuthScreenBinding) {

        vm.setOnAuthStateListener {
            Timber.d("Auth State AuthScreen => $it")
            when (it) {
                is AuthState.EnterPhone -> {
                    changePage(PhonePage.pagePhone())
                }

                is AuthState.EnterCode -> {
                    changePage(OTPPage.pageOTP())
                }

                is AuthState.EnterPassword -> {
                    changePage(PasswordPage.pagePassword(it.passwordHint))
                }

                is AuthState.LoginWithQRCode -> {
                    changePage(QRPage.pageQR(it.loginURL))
                }

                else -> {}
            }
        }
    }

    private fun changePage(page: Fragment) {
        childFragmentManager.commit {
            replace(R.id.frameLayout, page)
        }
    }
}
