package com.kyawhtut.atsycast.telegram.ui.auth.phone

import androidx.core.widget.addTextChangedListener
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

        vb.edtPhone.addTextChangedListener {
            it?.let {
                // Remove spacing char
                if (it.isNotEmpty() && (it.length % 5) == 0) {
                    val char = it[it.length - 1]
                    if (char == ' ') {
                        it.delete(it.length - 1, it.length)
                    }
                }
                // Insert char where needed.
                if (it.isNotEmpty() && (it.length % 5) == 0) {
                    val char = it[it.length - 1]
                    //only if its a digit where there should be a space we insert a space
                    if (char.isDigit() && it.toString().split(' ').size <= 2) {
                        // ****At this point you will check values and add space between them****
                        it.insert(it.length - 1, ' '.toString())
                    }
                }
            }
        }
    }
}