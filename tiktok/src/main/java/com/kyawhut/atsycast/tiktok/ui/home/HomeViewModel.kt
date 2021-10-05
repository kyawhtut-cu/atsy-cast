package com.kyawhut.atsycast.tiktok.ui.home

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.tiktok.data.network.response.VideoResponse
import com.kyawhut.atsycast.tiktok.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 10/4/21
 */
@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: HomeRepository
) : BaseViewModel() {

    companion object {
        private const val ON_LOAD_MORE_COUNT = 5
    }

    private val videoList: MutableList<VideoResponse> = mutableListOf()
    private var index: Int = -1

    private val routeName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_ROUTE_NAME) ?: ""
    }

    fun getVideoList(callback: (NetworkResponse<List<VideoResponse>>) -> Unit) {
        viewModelScope {
            repository.getVideoList(routeName) {
                if (it.isSuccess) videoList.addAll(it.data ?: listOf())
                callback(it)
            }
        }
    }

    private fun getVideo(): VideoResponse? {
        if (videoList.isEmpty()) return null
        if (index >= videoList.size || index <= -1) return null
        return videoList[index]
    }

    fun nextVideo(): VideoResponse? {
        if (videoList.isEmpty()) return null
        index += 1
        if (index > videoList.size - ON_LOAD_MORE_COUNT) viewModelScope {
            repository.getVideoList(routeName) {
                if (it.isSuccess) videoList.addAll(it.data ?: listOf())
            }
        }
        return getVideo()
    }

    fun previousVideo(): VideoResponse? {
        if (videoList.isEmpty() || index == 0) return null
        index -= 1
        return getVideo()
    }
}
