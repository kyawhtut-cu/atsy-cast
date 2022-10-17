package com.kyawhtut.atsycast.telegram.ui.auth.qr

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.fragment.app.viewModels
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.style.DrawableSource
import com.github.alexzhirkevich.customqrgenerator.style.QrLogoPadding
import com.github.alexzhirkevich.customqrgenerator.style.QrLogoShape
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.createQrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorColor
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.base.BaseFragment
import com.kyawhtut.atsycast.telegram.databinding.AuthPageQrBinding
import com.kyawhtut.atsycast.telegram.utils.AuthState
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
    private val option = createQrVectorOptions {

        logo {
            drawable = DrawableSource.Resource(R.drawable.ic_telegram_login)
            size = .25f
            padding = QrLogoPadding.Empty
            shape = QrLogoShape.Circle
        }

        colors {
            dark = QrVectorColor.Solid(Color.WHITE)
        }

        shapes {
            darkPixel = QrVectorPixelShape.RoundCorners(.5f)
            ball = QrVectorBallShape.RoundCorners(.25f)
            frame = QrVectorFrameShape.RoundCorners(.25f)
        }
    }

    override fun onViewCreated(vb: AuthPageQrBinding) {
        vb.vm = vm

        vm.setOnAuthStateListener {
            if (it is AuthState.LoginWithQRCode) {
                vm.loginURL = it.loginURL
                generateQRCode(it.loginURL)
            }
        }

        generateQRCode(vm.loginURL)

        generateQRCodeMessage()
    }

    private fun generateQRCode(loginURL: String) {
        val qrData = QrData.Url(loginURL)
        vb.ivQRCode.setImageDrawable(QrCodeDrawable(requireContext(), qrData, option))
    }

    private fun generateQRCodeMessage() {
        val startIndex: Int
        val endIndex: Int
        vb.tvPageMessage.text = SpannableString(
            getString(R.string.lblTelegramQRCodePageMessage).also {
                startIndex = it.indexOf("~~")
                endIndex = it.indexOf("~~~")
            }.replace("~", "")
        ).apply {
            setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex,
                endIndex,
                1
            )
        }
    }
}
