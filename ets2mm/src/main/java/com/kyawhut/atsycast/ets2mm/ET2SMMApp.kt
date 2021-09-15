package com.kyawhut.atsycast.ets2mm

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.kyawhut.atsycast.ets2mm.ui.home.HomeActivity
import com.kyawhut.atsycast.ets2mm.utils.Constants
import com.kyawhut.atsycast.share.utils.extension.put
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/15/21
 */
object ET2SMMApp {

    private fun Context.setAPIKey(apiKey: String) {
        with(PreferenceManager.getDefaultSharedPreferences(this)) {
            put(Constants.EXTRA_API_KEY, apiKey)
        }
    }

    internal fun Context.clearAPIKey() {
        setAPIKey("")
    }

    fun Context.goToETS2MM(appName: String, apiKey: String) {
        setAPIKey(apiKey)
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
        )
    }

    fun Fragment.goToETS2MM(appName: String, apiKey: String) {
        requireContext().goToETS2MM(appName, apiKey)
    }
}
