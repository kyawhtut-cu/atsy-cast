package com.kyawhut.atsycast.twod

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.twod.ui.home.HomeActivity

/**
 * @author kyawhtut
 * @date 9/23/21
 */
object TwoDApp {

    private fun Context.goTo2D() {
        startActivity<HomeActivity>()
    }

    fun FragmentActivity.goTo2D(appName: String) {
        goTo2D()
    }

    fun Fragment.goTo2D(appName: String) {
        requireContext().goTo2D()
    }
}
