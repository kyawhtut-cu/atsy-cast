package com.kyawhut.atsycast.zcm.ui.source

import android.os.Bundle
import androidx.activity.viewModels
import com.kyawhut.atsycast.share.base.BaseGuidedStepActivity
import com.kyawhut.atsycast.share.ui.resume.ResumeFragment
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.zcm.ui.player.PlayerActivity
import com.kyawhut.atsycast.zcm.utils.Constants
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
            vm.videoData?.moviesDescription ?: "",
            vm.videoData?.moviesCover ?: vm.videoData?.moviesImage ?: "",
            vm.videoData?.moviesGenres?.joinToString { it.genresTitle } ?: ""
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
                linkIndex = 0
                if (vm.isHasResume(vm.source.first().sourceId)) {
                    addAsRoot(resumeFragment)
                } else {
                    goToPlayer(false)
                    onBackPressed()
                }
            } else {
                addAsRoot(VideoSourceFragment())
            }
        }
    }

    fun onClickedLinkAction(id: Long) {
        linkIndex = id.toInt()
        if (vm.isHasResume(vm.source[linkIndex].sourceId)) add(resumeFragment)
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
            Constants.EXTRA_VIDEO_TITLE to vm.videoTitle,
            Constants.EXTRA_VIDEO_COVER to (vm.videoData?.moviesCover
                ?: vm.videoData?.moviesImage ?: ""),
            Constants.EXTRA_APP_NAME to vm.appName,
            Constants.EXTRA_CHANNEL_LOGO to vm.channelLogo,
            Constants.EXTRA_VIDEO_SOURCE to vm.source[linkIndex],
            Constants.EXTRA_RELATED_EPISODE to vm.relatedEpisode,
            Constants.EXTRA_IS_ADULT to (vm.videoData?.moviesGenres?.any { it.genresTitle.isAdult } == true),
        )
    }
}
