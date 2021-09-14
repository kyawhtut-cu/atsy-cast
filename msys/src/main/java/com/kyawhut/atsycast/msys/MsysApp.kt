package com.kyawhut.atsycast.msys

import android.content.Context
import androidx.fragment.app.Fragment
import com.kyawhut.atsycast.msys.ui.home.HomeActivity
import com.kyawhut.atsycast.msys.utils.Constants
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/10/21
 */
object MsysApp {

    fun Context.goToMSYS(appName: String, apiKey: String) {
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_API_KEY to apiKey
        )
    }

    fun Fragment.goToMSYS(appName: String, apiKey: String) {
        requireContext().goToMSYS(appName, apiKey)
    }

}
