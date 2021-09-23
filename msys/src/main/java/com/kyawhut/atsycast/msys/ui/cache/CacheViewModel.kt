package com.kyawhut.atsycast.msys.ui.cache

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse
import com.kyawhut.atsycast.msys.utils.Constants
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
    val apiKey: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }

    val recentlyWatch: LiveData<List<RecentlyWatchEntity>>
        get() = repository.getRecentlyWatch(application)

    val watchLater: LiveData<List<MoviesResponse>>
        get() = repository.getWatchLater(application)
}
