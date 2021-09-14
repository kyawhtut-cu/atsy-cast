package com.kyawhut.atsycast.msubpc.ui.football

import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.msubpc.data.network.response.FootballResponse
import com.kyawhut.atsycast.msubpc.data.network.response.FootballStreamResponse
import com.kyawhut.atsycast.msubpc.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/6/21
 */
@HiltViewModel
internal class FootballViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repo: FootballRepository
) : BaseViewModel() {

    val apiKey: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    var football: FootballResponse.Data? = null

    fun getFootball(
        callback: (NetworkResponse<List<FootballResponse.Data>>) -> Unit
    ) {
        viewModelScope {
            repo.getFootball(apiKey, callback)
        }
    }

    fun getFootballStream(
        football: FootballResponse.Data? = this.football,
        callback: (NetworkResponse<FootballStreamResponse>) -> Unit
    ) {
        this.football = football
        if (football == null) return
        viewModelScope {
            repo.getFootballStream(
                football.id,
                apiKey,
                callback
            )
        }
    }
}
