package com.kyawhut.atsycast.msubpc

import android.content.Context
import androidx.fragment.app.Fragment
import com.kyawhut.atsycast.msubpc.ui.home.HomeActivity
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/9/21
 */
object MsubPC {

    fun Context.goToMsubPC(appName: String, apiKey: String) {
        startActivity<HomeActivity>(
            Constants.EXTRA_APP_NAME to appName,
            Constants.EXTRA_API_KEY to apiKey
        )
    }

    fun Fragment.goToMsubPC(appName: String, apiKey: String) {
        requireContext().goToMsubPC(appName, apiKey)
    }
}
