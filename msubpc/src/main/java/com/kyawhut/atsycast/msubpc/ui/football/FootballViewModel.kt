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

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }
    var football: FootballResponse? = null

    fun getFootball(
        callback: (NetworkResponse<List<FootballResponse>>) -> Unit
    ) {
        viewModelScope {
            repo.getFootball(callback)
        }
    }

    fun getFootballStream(
        football: FootballResponse? = this.football,
        callback: (NetworkResponse<List<FootballStreamResponse>>) -> Unit
    ) {
        this.football = football
        if (football == null) return
        viewModelScope {
            repo.getFootballStream(
                football.id,
                callback
            )
        }
    }
}
