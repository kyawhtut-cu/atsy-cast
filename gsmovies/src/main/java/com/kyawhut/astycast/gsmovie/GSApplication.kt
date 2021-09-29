package com.kyawhut.astycast.gsmovie

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kyawhut.astycast.gsmovie.ui.home.HomeActivity
import com.kyawhut.astycast.gsmovie.utils.Constants
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/29/21
 */
object GSApplication {

    private fun goToHome(context: Context, appName: String, apiKey: String) {
        context.startActivity<HomeActivity>(
            Constants.EXTRA_API_KEY to apiKey,
            Constants.EXTRA_APP_NAME to appName,
        )
    }

    fun FragmentActivity.goToGSHome(apiKey: String, appName: String) {
        goToHome(this, appName, apiKey)
    }

    fun Fragment.goToGSHome(apiKey: String, appName: String) {
        goToHome(requireContext(), appName, apiKey)
    }
}
