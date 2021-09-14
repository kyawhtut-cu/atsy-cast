package com.kyawhut.atsycast.share.utils.extension

import android.app.Activity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit

object FragmentExtension {
    fun FragmentActivity.replaceFragment(fragment: Fragment) {
        supportFragmentManager.replaceFragment(fragment = fragment)
    }

    fun FragmentActivity.addFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.addFragment(fragment = fragment, tag = tag)
    }

    fun FragmentActivity.popBackStack(name: String, flags: Int) {
        supportFragmentManager.popBackStackImmediate(name, flags)
    }

    fun FragmentActivity.finish(
        resultCode: Int = Activity.RESULT_OK,
        vararg data: Pair<String, Any>
    ) {
        setResult(resultCode, intent?.apply {
            putExtras(IntentExtension.getBundle(data))
        })
        finish()
    }

    fun FragmentActivity.replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment
    ) {
        supportFragmentManager.replaceFragment(containerViewId, fragment)
    }

    fun Fragment.replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment
    ) {
        childFragmentManager.replaceFragment(containerViewId, fragment)
    }

    fun FragmentManager.replaceFragment(
        @IdRes containerViewId: Int = android.R.id.content,
        fragment: Fragment
    ) {
        commit {
            replace(containerViewId, fragment)
        }
    }

    fun FragmentManager.addFragment(
        @IdRes containerViewId: Int = android.R.id.content,
        fragment: Fragment,
        tag: String
    ) {
        commit {
            add(containerViewId, fragment)
            addToBackStack(tag)
        }
    }
}
