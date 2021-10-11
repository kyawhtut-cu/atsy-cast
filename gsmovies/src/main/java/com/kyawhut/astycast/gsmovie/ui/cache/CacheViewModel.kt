package com.kyawhut.astycast.gsmovie.ui.cache

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse
import com.kyawhut.astycast.gsmovie.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.db.entity.RecentlyWatchEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/9/21
 */
@HiltViewModel
internal class CacheViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: CacheRepository
) : BaseViewModel() {

    val cacheKeyType: String by lazy {
        savedStateHandle.get(Constants.EXTRA_PAGE_KEY) ?: ""
    }
    val route: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }

    val recentlyWatch: LiveData<List<RecentlyWatchEntity>>
        get() = repository.getRecentlyWatch(route)

    val watchLater: LiveData<List<VideoResponse.Data>>
        get() = repository.getWatchLater(route)
}
