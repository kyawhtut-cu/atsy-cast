package com.kyawhtut.atsycast.telegram.ui.main

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.base.BaseFragment
import com.kyawhtut.atsycast.telegram.databinding.MainScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class MainScreen : BaseFragment<MainScreenBinding>(R.layout.main_screen) {

    companion object {
        fun screenMain(): MainScreen {
            return MainScreen()
        }
    }

    private val vm: MainViewModel by viewModels()

    override fun onCreateView(vb: MainScreenBinding, savedInstanceState: Bundle?) {
        vb.vm = vm
    }
}
