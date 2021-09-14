package com.kyawhut.atsycast.share.base

import android.os.Bundle
import android.view.KeyEvent
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.replaceFragment

/**
 * @author kyawhtut
 * @date 9/7/21
 */
abstract class BaseDetailTvActivity<FM : BaseDetailTvFragment<*>> : FragmentActivity() {

    abstract val fm: FM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) replaceFragment(fm)
    }

    @CallSuper
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        with(supportFragmentManager.findFragmentById(android.R.id.content)) {
            if (this != null) {
                if (this is BaseDetailTvFragment<*>) {
                    this.toggleGuideBottom(keyCode)
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    @CallSuper
    override fun onBackPressed() {
        with(supportFragmentManager.findFragmentById(android.R.id.content)) {
            if (this == null || this !is BaseDetailTvFragment<*> || !this.isOpenFullScreenDescription) {
                super.onBackPressed()
                return@with
            }
            this.showFullScreenDescription(false)
        }
    }
}
