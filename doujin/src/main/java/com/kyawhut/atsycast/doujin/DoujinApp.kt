package com.kyawhut.atsycast.doujin

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import com.kyawhut.atsycast.doujin.ui.home.HomeActivity
import com.kyawhut.atsycast.doujin.utils.Constants
import com.kyawhut.atsycast.share.utils.extension.put
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/15/21
 */
object DoujinApp {

    private fun Context.setAPIKey(apiKey: String) {
        with(PreferenceManager.getDefaultSharedPreferences(this)) {
            put(
                Constants.EXTRA_PACKAGE_NAME,
                if (apiKey.isEmpty()) "" else apiKey.split("#").first()
            )
            put(Constants.EXTRA_APP_VERSION, if (apiKey.isEmpty()) "" else apiKey.split("#").last())
        }
    }

    internal fun Context.clearAPIKey() {
        setAPIKey("")
    }

    fun Fragment.goToDoujin(appName: String, channelLogo: String, apiKey: String) {
        requireContext().setAPIKey(apiKey)
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
        )
    }

    fun FragmentActivity.goToDoujin(appName: String, channelLogo: String, apiKey: String) {
        setAPIKey(apiKey)
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_CHANNEL_LOGO to channelLogo,
        )
    }
}
