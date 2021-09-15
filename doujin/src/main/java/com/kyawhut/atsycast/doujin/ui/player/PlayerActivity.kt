package com.kyawhut.atsycast.doujin.ui.player

import android.os.Bundle
import androidx.activity.viewModels
import com.kyawhut.atsycast.doujin.BuildConfig
import com.kyawhut.atsycast.share.base.BasePlayerActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@AndroidEntryPoint
class PlayerActivity : BasePlayerActivity() {

    private val vm: PlayerViewModel by viewModels()
    override val appName: String
        get() = vm.appName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (vm.videoSource == null || vm.videoData == null) {
            finishAndRemoveTask()
            return
        }

        play(
            vm.videoData?.doujinTitle ?: "",
            BuildConfig.MEDIA_BASE_URL + vm.videoData?.doujinImage ?: "",
            vm.videoSource!!,
        )
    }

    override fun onRelatedItemClicked(item: Any) {
    }
}
