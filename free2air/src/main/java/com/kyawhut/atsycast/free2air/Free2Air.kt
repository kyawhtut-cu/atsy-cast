package com.kyawhut.atsycast.free2air

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.kyawhut.atsycast.free2air.ui.home.HomeActivity
import com.kyawhut.atsycast.free2air.utils.Constants
import com.kyawhut.atsycast.share.utils.extension.startActivity

/**
 * @author kyawhtut
 * @date 9/9/21
 */
object Free2Air {
    fun FragmentActivity.goToFree2Air(key: String, title: String) {
        startActivity<HomeActivity>(
            Constants.EXTRA_API_KEY to key,
            Constants.EXTRA_APP_NAME to title,
        )
    }

    fun Fragment.goToFree2Air(key: String, title: String) {
        startActivity<HomeActivity>(
            Constants.EXTRA_API_KEY to key,
            Constants.EXTRA_APP_NAME to title,
        )
    }
}
