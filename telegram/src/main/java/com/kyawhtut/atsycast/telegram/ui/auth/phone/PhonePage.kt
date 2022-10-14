package com.kyawhtut.atsycast.telegram.ui.auth.phone

import androidx.fragment.app.viewModels
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.base.BaseFragment
import com.kyawhtut.atsycast.telegram.databinding.AuthPagePhoneBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class PhonePage : BaseFragment<AuthPagePhoneBinding>(R.layout.auth_page_phone) {

    companion object {
        fun pagePhone(): PhonePage {
            return PhonePage()
        }
    }

    private val vm: PhoneViewModel by viewModels()

    override fun onViewCreated(vb: AuthPagePhoneBinding) {
        vb.vm = vm
    }
}
