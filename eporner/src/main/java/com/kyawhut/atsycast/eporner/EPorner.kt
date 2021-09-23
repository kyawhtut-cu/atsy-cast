package com.kyawhut.atsycast.eporner

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kyawhut.atsycast.eporner.ui.home.HomeActivity
import com.kyawhut.atsycast.eporner.utils.Constants
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/22/21
 */
object EPorner {

    private fun Context.goToEPorner(vararg data: Pair<String, Any>) {
        startActivity<HomeActivity>(*data)
    }

    fun FragmentActivity.goToEPorner(appName: String) {
        goToEPorner(
            Constants.EXTRA_APP_NAME to appName
        )
    }

    fun Fragment.goToEPorner(appName: String) {
        requireContext().goToEPorner(
            Constants.EXTRA_APP_NAME to appName
        )
    }
}
