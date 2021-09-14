package com.kyawhut.atsycast.share.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import androidx.leanback.app.GuidedStepSupportFragment
import com.kyawhut.atsycast.share.ui.dialog.LoadingDialog

/**
 * @author kyawhtut
 * @date 9/7/21
 */
abstract class BaseGuidedStepActivity : FragmentActivity() {

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(supportFragmentManager)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showLoading() {
        loadingDialog.show()
    }

    fun hideLoading() {
        loadingDialog.hide()
    }

    fun addAsRoot(fragment: GuidedStepSupportFragment) {
        GuidedStepSupportFragment.addAsRoot(this, fragment, android.R.id.content)
    }

    fun add(fragment: GuidedStepSupportFragment) {
        GuidedStepSupportFragment.add(supportFragmentManager, fragment)
    }

    open fun onClickedAction(id: Long) {}
}
