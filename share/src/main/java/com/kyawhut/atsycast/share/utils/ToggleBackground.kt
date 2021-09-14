package com.kyawhut.atsycast.share.utils

import android.os.CountDownTimer

/**
 * @author kyawhtut
 * @date 9/2/21
 */
class ToggleBackground {

    private val randomPhoto = listOf(
        "https://source.unsplash.com/random/1600x900/",
        "https://picsum.photos/1600/900",
        "https://random.imagecdn.app/1600/900"
    )
    private var randomPhotoIndex: Int = 0
    var callBack: (String) -> Unit = {}
        set(value) {
            field = value
            value(randomPhoto[randomPhotoIndex])
        }

    private val countDown: CountDownTimer by lazy {
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                randomPhotoIndex += 1
                callBack(randomPhoto[randomPhotoIndex])
                if (randomPhotoIndex >= randomPhoto.size - 1) {
                    randomPhotoIndex = -1
                }
                stop()
                start()
            }
        }
    }

    fun start() {
        countDown.start()
    }

    fun stop() {
        countDown.cancel()
    }
}
