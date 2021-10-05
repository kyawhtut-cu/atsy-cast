package com.kyawhut.atsycast.tiktok

import android.content.Context
import androidx.fragment.app.Fragment
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.tiktok.ui.home.HomeActivity
import com.kyawhut.atsycast.tiktok.utils.Constants

/**
 * @author kyawhtut
 * @date 10/4/21
 */
object TiktokApp {

    private fun goToTiktok(routeName: String, context: Context) {
        context.startActivity<HomeActivity>(
            Constants.EXTRA_ROUTE_NAME to routeName
        )
    }

    fun Fragment.goToTiktok(routeName: String) {
        goToTiktok(routeName, requireContext())
    }
}
