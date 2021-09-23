package com.kyawhut.atsycast.msys.ui.source

import android.os.Bundle
import androidx.activity.viewModels
import com.kyawhut.atsycast.msys.ui.player.PlayerActivity
import com.kyawhut.atsycast.msys.utils.Constants
import com.kyawhut.atsycast.msys.utils.cloudmd.CloudMB
import com.kyawhut.atsycast.share.base.BaseGuidedStepActivity
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.ui.dialog.PlayerErrorDialog.Companion.showError
import com.kyawhut.atsycast.share.ui.resume.ResumeFragment
import com.kyawhut.atsycast.share.utils.ShareUtils.isAdult
import com.kyawhut.atsycast.share.utils.ShareUtils.isDirectPlayExtension
import com.kyawhut.atsycast.share.utils.ShareUtils.isEmbedExtension
import com.kyawhut.atsycast.share.utils.ShareUtils.isWEBMExtension
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
                processSource()
            } else {
                addAsRoot(VideoSourceFragment())
            }
        }
    }

    private fun processSource() {
        val source = vm.source[linkIndex]
        when {
            source.sourceType.isDirectPlayExtension -> {
                if (vm.isHasResume(vm.source.first().sourceId)) {
                    if (vm.source.size == 1) addAsRoot(resumeFragment)
                    else add(resumeFragment)
                } else {
                    goToPlayer(false)
                    if (vm.source.size == 1) onBackPressed()
                }
            }
            source.sourceType.isWEBMExtension -> {
                processCloudMB(source)
            }
            source.sourceType.isEmbedExtension -> {
                processRedirect(source)
            }
        }
    }

    private fun processRedirect(source: VideoSourceModel) {
        showLoading()
        vm.getRedirectURL(source.url) { status, data ->
            hideLoading()
            if (status) {
                vm.sourceURL = data
                if (vm.isHasResume(vm.source[linkIndex].sourceId)) {
                    if (vm.source.size == 1) addAsRoot(resumeFragment)
                    else add(resumeFragment)
                } else {
                    goToPlayer(false)
                    if (vm.source.size == 1) onBackPressed()
                }
            } else {
                showError("Error Redirect URL") {
                    processRedirect(source)
                }
            }
        }
    }

    private fun processCloudMB(source: VideoSourceModel) {
        showLoading()
        CloudMB.fetch(source.url) { cloudMBStream ->
            hideLoading()
            if (cloudMBStream.isSuccessful) {
                vm.cloudMBSource = cloudMBStream.streams
                if (vm.source.size == 1) addAsRoot(CloudMBSourceFragment())
                else add(CloudMBSourceFragment())
            } else {
                showError("Error Cloud MB") {
                    processCloudMB(source)
                }
            }
        }
    }

    fun onClickedLinkAction(id: Long) {
        linkIndex = id.toInt()
        processSource()
    }

    fun onClickedCloudMBLinkAction(id: Long) {
        vm.sourceURL = vm.cloudMBSource[id.toInt()].url
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
            Constants.EXTRA_IS_ADULT to (vm.videoData?.moviesGenres?.any { it.genresTitle.isAdult } == true),
            Constants.EXTRA_VIDEO_SOURCE to vm.source[linkIndex].apply {
                if (vm.sourceURL.isNotEmpty())
                    url = vm.sourceURL
            }.also {
                vm.sourceURL = ""
            },
            Constants.EXTRA_RELATED_EPISODE to vm.relatedEpisode
        )
    }
}
