package com.kyawhut.atsycast.msubpc

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.kyawhut.atsycast.msubpc.ui.home.HomeActivity
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.utils.extension.put
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/9/21
 */
object MsubPC {

    private fun Context.setAPIKey(apiKey: String) {
        with(PreferenceManager.getDefaultSharedPreferences(this)) {
            put(Constants.EXTRA_API_KEY, apiKey)
        }
    }

    internal fun Context.clearAPIKey() {
        setAPIKey("")
    }

    fun Context.goToMsubPC(appName: String, apiKey: String) {
        setAPIKey(apiKey)
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
        )
    }

    fun Fragment.goToMsubPC(appName: String, apiKey: String) {
        requireContext().goToMsubPC(appName, apiKey)
    }
}
