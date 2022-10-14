package com.kyawhtut.atsycast.telegram.ui.auth.qr

import androidx.fragment.app.viewModels
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.base.BaseFragment
import com.kyawhtut.atsycast.telegram.databinding.AuthPageQrBinding
import com.kyawhut.atsycast.share.utils.extension.putArg
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class QRPage : BaseFragment<AuthPageQrBinding>(R.layout.auth_page_qr) {

    companion object {
        fun pageQR(loginURL: String): QRPage {
            return QRPage().putArg(
                QRViewModel.EXTRA_LOGIN_URL to loginURL
            )
        }
    }

    private val vm: QRViewModel by viewModels()

    override fun onViewCreated(vb: AuthPageQrBinding) {
        vb.vm = vm

        vm.setQRCodeImage(vb.ivQRCode)
    }
}
