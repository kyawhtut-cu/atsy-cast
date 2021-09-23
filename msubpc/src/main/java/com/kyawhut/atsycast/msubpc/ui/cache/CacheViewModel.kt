package com.kyawhut.atsycast.msubpc.ui.cache

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse
import com.kyawhut.atsycast.msubpc.utils.Constants
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
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val repository: CacheRepository
) : BaseViewModel() {

    val cacheKeyType: String by lazy {
        savedStateHandle.get(Constants.EXTRA_PAGE_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }

    val recentlyWatch: LiveData<List<RecentlyWatchEntity>>
        get() = repository.getRecentlyWatch(application)

    val watchLater: LiveData<List<VideoResponse>>
        get() = repository.getWatchLater(application)
}
