package com.kyawhut.atsycast.share.utils.player

import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.Player
import timber.log.Timber

internal class ComponentListener(
    private val playerState: (Int) -> Unit,
    private val playerError: (ExoPlaybackException) -> Unit
) : Player.Listener {
    override fun onIsPlayingChanged(isPlaying: Boolean) {
    }

    override fun onSeekProcessed() {
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        error.printStackTrace()
        playerError(error)
    }

    override fun onLoadingChanged(isLoading: Boolean) {
    }

    override fun onPositionDiscontinuity(reason: Int) {
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        playerState(playbackState)
        val state: String
        when (playbackState) {
            Player.STATE_IDLE -> {
                state = "ExoPlayer.STATE_IDLE      -"
            }
            Player.STATE_BUFFERING -> {
                /*hideAll()
                loadingState(true)*/
                state = "ExoPlayer.STATE_BUFFERING -"
            }
            Player.STATE_READY -> {
//                hideAll()
                state = "ExoPlayer.STATE_READY     -"
            }
            Player.STATE_ENDED -> {
                state = "ExoPlayer.STATE_ENDED     -"
                /*hideAll()
                endPlayer()*/
            }
            else -> {
                state = "UNKNOWN_STATE             -"
            }
        }
        Timber.d(state)
    }
}