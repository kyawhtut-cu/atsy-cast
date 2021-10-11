package com.kyawhut.atsycast.gs_mm.ui.detail

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.gs_mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.RelatedResponse
import com.kyawhut.atsycast.gs_mm.data.network.response.VideoResponse
import com.kyawhut.atsycast.gs_mm.utils.Constants
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@HiltViewModel
internal class DetailViewModel @Inject constructor(
    private val application: Application,
    private val savedStateHandle: SavedStateHandle,
    private val repository: DetailRepository,
) : BaseViewModel() {

    val appName: String by lazy {
        savedStateHandle.get(Constants.EXTRA_APP_NAME) ?: ""
    }
    val channelLogo: String by lazy {
        savedStateHandle.get(Constants.EXTRA_CHANNEL_LOGO) ?: ""
    }
    val route: String by lazy {
        savedStateHandle.get(Constants.EXTRA_API_KEY) ?: ""
    }
    val videoData: VideoResponse? by lazy {
        savedStateHandle.get(Constants.EXTRA_VIDEO_DATA) as VideoResponse?
    }
    val isWatchLater: Boolean
        get() = repository.isWatchLater(route, videoData!!.videoID)

    fun toggleWatchLater() {
        repository.toggleWatchLater(route, videoData!!)
    }

    private var playlistPage: Int = 1
    val isFirstPlaylistPage: Boolean
        get() = playlistPage == 1
    private var isLoadingPlaylist: Boolean = false
    private var playlistTotal: Int = 0
    private var serverPlaylistTotal: Int = -1

    private var episodePage: Int = 1
    val isFirstEpisodePage: Boolean
        get() = episodePage == 1
    private var isLoadingEpisode: Boolean = false
    private var episodeTotal: Int = 0

    fun getRelateMovies(callback: (NetworkResponse<RelatedResponse.Data>) -> Unit) {
        viewModelScope {
            repository.getRelatedMovies(
                application,
                route,
                videoData!!.videoID,
                callback
            )
        }
    }

    fun getPlaylistList(callback: (NetworkResponse<RelatedResponse.Data>) -> Unit) {
        if (videoData!!.playlistID.isEmpty() || isLoadingPlaylist || playlistTotal == serverPlaylistTotal) return
        viewModelScope {
            repository.getPlaylist(application, route, videoData!!.playlistID, playlistPage) {
                callback(it)
                isLoadingPlaylist = it.isLoading
                if (it.isSuccess) {
                    playlistTotal += it.data?.videoList?.size ?: 0
                    if (serverPlaylistTotal == -1) serverPlaylistTotal = it.data?.total ?: 0
                    playlistPage += 1
                }
            }
        }
    }

    fun getSeriesSeason(callback: (NetworkResponse<List<EpisodeResponse.Data>>) -> Unit) {
        if (isLoadingEpisode || episodeTotal == videoData!!.videoEpisodeTotal) return
        viewModelScope {
            repository.getSeriesSeason(
                application,
                route,
                videoData!!.videoID,
                episodePage
            ) {
                callback(it)
                isLoadingEpisode = it.isLoading
                if (it.isSuccess) {
                    episodeTotal += it.data?.size ?: 0
                    episodePage += 1
                }
            }
        }
    }
}
