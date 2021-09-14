package com.kyawhut.atsycast.share.utils.player

/**
 * @author kyawhtut
 * @date 9/1/21
 */
interface PlayerManager {

    fun toggleLoading(isLoading: Boolean) {
    }

    fun showPoster() {
    }

    fun hideAll() {
    }

    fun onPlayerControlState(isVisible: Boolean) {}

    fun onPlayerPosition(position: Long) {}

    fun onPlayerBufferPosition(position: Long) {}

    fun onPlayerDestroy(videoDuration: Long, playerCurrentPosition: Long) {}

    fun onPlayerEnd() {}

    fun onPlayerError(error: String, playerData: String) {}
}
