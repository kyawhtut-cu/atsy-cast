package com.kyawhut.atsycast.share.base

import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import androidx.fragment.app.FragmentActivity
import com.kyawhut.atsycast.share.utils.extension.FragmentExtension.replaceFragment

/**
 * @author kyawhtut
 * @date 9/7/21
 */
abstract class BaseSearchTvActivity<FM : BaseSearchSupportFragment<*>> : FragmentActivity() {

    abstract val searchFragment: FM

    private var isDoubleBackToExitPressedOnce = false

    open fun onResultNotFound() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) replaceFragment(searchFragment)
    }

    override fun onSearchRequested(): Boolean {
        if (searchFragment.isResultFound) {
            onResultNotFound()
        } else {
            searchFragment.startRecognition()
        }
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT && !searchFragment.isResultFound) {
            searchFragment.focusOnSearch()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (isDoubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        isDoubleBackToExitPressedOnce = true

//        Toasty.warning(this, "Click back again to exit.", Toasty.LENGTH_LONG).show()

        object : CountDownTimer(2000, 1000) {
            override fun onFinish() {
                isDoubleBackToExitPressedOnce = false
            }

            override fun onTick(millisUntilFinished: Long) {
            }
        }.start()
    }
}
