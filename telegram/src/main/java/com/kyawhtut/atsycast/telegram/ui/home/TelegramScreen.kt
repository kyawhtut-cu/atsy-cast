package com.kyawhtut.atsycast.telegram.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.databinding.HomeScreenBinding
import com.kyawhtut.atsycast.telegram.ui.auth.AuthScreen
import com.kyawhtut.atsycast.telegram.ui.main.MainScreen
import com.kyawhtut.atsycast.telegram.utils.AuthState
import com.kyawhut.atsycast.share.base.BaseTvActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TelegramScreen : BaseTvActivity<HomeScreenBinding>() {

    override val layoutID: Int
        get() = R.layout.home_screen

    private val vm: TelegramViewModel by viewModels()
    private val isAddedAuthPage: Boolean
        get() {
            val current = supportFragmentManager.findFragmentById(R.id.homeFrame) ?: return false
            return current is AuthScreen
        }
    private val isAddedMain: Boolean
        get() {
            val current = supportFragmentManager.findFragmentById(R.id.homeFrame) ?: return false
            return current is MainScreen
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb.vm = vm

        vm.setOnAuthStateListener {
            Timber.d("Auth State TelegramScreen => $it")
            if (it is AuthState.LoggedIn) {
                if (isAddedMain) return@setOnAuthStateListener
                supportFragmentManager.commit {
                    replace(R.id.homeFrame, MainScreen.screenMain())
                }
            } else {
                if (isAddedAuthPage) return@setOnAuthStateListener
                supportFragmentManager.commit {
                    replace(R.id.homeFrame, AuthScreen.screenAuth(it))
                }
            }
        }
    }
}