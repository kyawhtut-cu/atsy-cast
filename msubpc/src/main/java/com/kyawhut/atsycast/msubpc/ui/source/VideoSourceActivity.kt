package com.kyawhut.atsycast.msubpc.ui.source

import android.os.Bundle
import androidx.activity.viewModels
import com.kyawhut.atsycast.msubpc.ui.player.PlayerActivity
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseGuidedStepActivity
import com.kyawhut.atsycast.share.ui.resume.ResumeFragment
import com.kyawhut.atsycast.share.utils.extension.startActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@AndroidEntryPoint
class VideoSourceActivity : BaseGuidedStepActivity() {

    private val vm: VideoSourceViewModel by viewModels()
    private var linkIndex: Int = -1

    private val resumeFragment: ResumeFragment by lazy {
        ResumeFragment.newResumeFragment(
            vm.videoTitle,
            vm.videoData?.videoReview ?: "",
            vm.videoData?.videoCoverImage ?: vm.videoData?.videoPosterImage ?: "",
            vm.videoData?.videoGenres ?: ""
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (vm.source.isEmpty() || vm.videoData == null) {
            finishAndRemoveTask()
            return
        }

        if (savedInstanceState == null) {
            if (vm.source.size == 1) {
                if (vm.isHasResume(vm.source.first().sourceId) && !vm.isLive) {
                    linkIndex = 0
                    addAsRoot(resumeFragment)
                } else {
                    goToPlayer(false)
                }
            } else {
                addAsRoot(VideoSourceFragment())
            }
        }
    }

    fun onClickedLinkAction(id: Long) {
        linkIndex = id.toInt()
        if (vm.isHasResume(vm.source[linkIndex].sourceId) && !vm.isLive) add(resumeFragment)
        else goToPlayer(false)
    }

    override fun onClickedAction(id: Long) {
        goToPlayer(id == 1L)
        onBackPressed()
    }

    private fun goToPlayer(isResume: Boolean) {
        startActivity<PlayerActivity>(
            Constants.EXTRA_VIDEO_ID to vm.source[linkIndex].sourceId,
            Constants.EXTRA_IS_RESUME to isResume,
            Constants.EXTRA_IS_LIVE to vm.isLive,
            Constants.EXTRA_VIDEO_TITLE to vm.videoTitle,
            Constants.EXTRA_VIDEO_COVER to (vm.videoData!!.videoCoverImage
                ?: vm.videoData!!.videoPosterImage),
            Constants.EXTRA_APP_NAME to vm.appName,
            Constants.EXTRA_VIDEO_SOURCE to vm.source[linkIndex],
            Constants.EXTRA_RELATED_EPISODE to vm.relatedEpisode
        )
    }
}
