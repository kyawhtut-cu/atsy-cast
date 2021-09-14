package com.kyawhut.atsycast.share.ui.resume

import androidx.leanback.widget.GuidedAction
import com.kyawhut.atsycast.share.base.BaseGuidedStepActivity
import com.kyawhut.atsycast.share.base.BaseGuidedStepSupportFragment
import com.kyawhut.atsycast.share.utils.extension.putArg

/**
 * @author kyawhtut
 * @date 9/8/21
 */
class ResumeFragment : BaseGuidedStepSupportFragment() {

    companion object {
        fun newResumeFragment(
            videoTitle: String,
            videoDescription: String,
            videoCover: String,
            breadcrumb: String
        ): ResumeFragment {
            return ResumeFragment().putArg(
                "videoTitle" to videoTitle,
                "videoDescription" to videoDescription,
                "videoCover" to videoCover,
                "breadcrumb" to breadcrumb,
            )
        }
    }

    override val title: String
        get() = arguments?.getString("videoTitle") ?: ""
    override val description: String
        get() = arguments?.getString("videoDescription") ?: ""
    override val breadcrumb: String
        get() = arguments?.getString("breadcrumb") ?: ""
    private val videoCover: String
        get() = arguments?.getString("videoCover") ?: ""

    override fun onViewCreated() {
        setLogo(videoCover)
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>) {
        addAction(actions, 1L, "Resume", "Play with resume.")
        addAction(actions, 2L, "Start", "Play from start.")
    }

    override fun onClickAction(id: Long) {
        (requireActivity() as BaseGuidedStepActivity).onClickedAction(id)
    }
}
