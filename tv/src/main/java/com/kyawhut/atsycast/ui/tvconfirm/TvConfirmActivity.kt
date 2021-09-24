package com.kyawhut.atsycast.ui.tvconfirm

import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.databinding.ActivityTvConfirmBinding
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.utils.extension.Extension.isTvConfirm
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.ui.splash.SplashActivity
import es.dmoral.toasty.Toasty

/**
 * @author kyawhtut
 * @date 9/24/21
 */
class TvConfirmActivity : BaseTvActivity<ActivityTvConfirmBinding>() {

    override val layoutID: Int
        get() = R.layout.activity_tv_confirm

    enum class RemoteKey(val remote: Int) {
        TOP(R.drawable.mi_remote_top),
        RIGHT(R.drawable.mi_remote_right),
        BOTTOM(R.drawable.mi_remote_bottom),
        LEFT(R.drawable.mi_remote_left),
        CENTER(R.drawable.mi_remote_ok),
        DEFAULT(0);

        companion object {
            fun getRemoteKey(keyCode: Int): RemoteKey {
                return when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_UP -> TOP
                    KeyEvent.KEYCODE_DPAD_RIGHT -> RIGHT
                    KeyEvent.KEYCODE_DPAD_DOWN -> BOTTOM
                    KeyEvent.KEYCODE_DPAD_LEFT -> LEFT
                    KeyEvent.KEYCODE_ENTER -> CENTER
                    else -> DEFAULT
                }
            }
        }
    }

    private var clickedCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        changeKeyFocus(RemoteKey.TOP)

        isTvConfirm = false
    }

    private fun changeKeyFocus(key: RemoteKey) {
        if (key == RemoteKey.DEFAULT) {
            isTvConfirm = true
            Handler().postDelayed({
                finishAndRemoveTask()
                startActivity<SplashActivity>()
            }, 500)
            return
        }
        vb.apply {
            currentRemoteFocus = when (key) {
                RemoteKey.TOP -> getString(R.string.lbl_remote_top)
                RemoteKey.RIGHT -> getString(R.string.lbl_remote_right)
                RemoteKey.BOTTOM -> getString(R.string.lbl_remote_bottom)
                RemoteKey.LEFT -> getString(R.string.lbl_remote_left)
                RemoteKey.CENTER -> getString(R.string.lbl_remote_center)
                RemoteKey.DEFAULT -> currentRemoteFocus
            }
            ivCurrentFocus.setImageResource(key.remote)
            executePendingBindings()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val clickedKey = RemoteKey.getRemoteKey(keyCode)
        when {
            clickedKey == RemoteKey.TOP && clickedCount == 0 -> {
                clickedCount = 1
                changeKeyFocus(RemoteKey.RIGHT)
            }
            clickedKey == RemoteKey.RIGHT && clickedCount == 1 -> {
                clickedCount = 2
                changeKeyFocus(RemoteKey.BOTTOM)
            }
            clickedKey == RemoteKey.BOTTOM && clickedCount == 2 -> {
                clickedCount = 3
                changeKeyFocus(RemoteKey.LEFT)
            }
            clickedKey == RemoteKey.LEFT && clickedCount == 3 -> {
                clickedCount = 4
                changeKeyFocus(RemoteKey.CENTER)
            }
            clickedKey == RemoteKey.CENTER && clickedCount == 4 -> {
                clickedCount = 5
                changeKeyFocus(RemoteKey.DEFAULT)
            }
            else -> {
                Toasty.warning(this, vb.currentRemoteFocus.toString(), Toasty.LENGTH_LONG).show()
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
