package com.kyawhut.atsycast.free2air.ui.home

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.free2air.data.network.response.Free2AirResponse
import com.kyawhut.atsycast.free2air.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val argument: SavedStateHandle,
    private val repository: HomeRepository
) : BaseViewModel() {

    val appName: String by lazy {
        argument.get(Constants.EXTRA_APP_NAME) ?: ""
    }

    fun getFree2Air(
        callback: (NetworkResponse<HashMap<String, List<Free2AirResponse.Data>>>) -> Unit
    ) {
        viewModelScope {
            repository.getFree2Air(argument.get<String>(Constants.EXTRA_API_KEY) ?: "", callback)
        }
    }
}
