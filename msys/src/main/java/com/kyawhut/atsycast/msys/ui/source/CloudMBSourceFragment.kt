package com.kyawhut.atsycast.msys.ui.source

import androidx.fragment.app.activityViewModels
import androidx.leanback.widget.GuidedAction
import com.kyawhut.atsycast.share.base.BaseGuidedStepSupportFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@AndroidEntryPoint
class CloudMBSourceFragment : BaseGuidedStepSupportFragment() {

    private val vm: VideoSourceViewModel by activityViewModels()

    override val title: String
        get() = vm.videoTitle
    override val description: String
        get() = vm.videoData?.moviesDescription ?: ""
    override val breadcrumb: String
        get() = vm.videoData?.moviesGenres?.joinToString { it.genresTitle } ?: ""

    override fun onViewCreated() {
        setLogo(vm.videoData!!.moviesCover ?: vm.videoData!!.moviesImage)
    }

    override fun onCreateActions(actions: MutableList<GuidedAction>) {
        vm.cloudMBSource.forEachIndexed { index, source ->
            addAction(actions, index.toLong(), source.quality, source.fileSize)
        }
    }

    override fun onClickAction(id: Long) {
        (requireActivity() as VideoSourceActivity).onClickedCloudMBLinkAction(id)
    }
}
