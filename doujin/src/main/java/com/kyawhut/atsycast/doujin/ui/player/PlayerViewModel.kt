package com.kyawhut.atsycast.doujin.ui.player

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.doujin.data.network.response.DoujinVideoDetailResponse
import com.kyawhut.atsycast.doujin.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.model.VideoSourceModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@HiltViewModel
internal class PlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val videoData: DoujinVideoDetailResponse? by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_DATA) as DoujinVideoDetailResponse?
    }
    val videoSource: VideoSourceModel? by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_SOURCE) as VideoSourceModel?
    }
}
