package com.kyawhut.atsycast.zcm

import android.content.Context
import androidx.fragment.app.Fragment
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.zcm.ui.home.HomeActivity
import com.kyawhut.atsycast.zcm.utils.Constants

/**
 * @author kyawhtut
 * @date 9/10/21
 */
object ZCMApp {

    fun Context.goToZCM(appName: String, apiKey: String) {
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_API_KEY to apiKey
        )
    }

    fun Fragment.goToZCM(appName: String, apiKey: String) {
        requireContext().goToZCM(appName, apiKey)
    }

}
