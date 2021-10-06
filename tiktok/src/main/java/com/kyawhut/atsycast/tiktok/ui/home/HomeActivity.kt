package com.kyawhut.atsycast.tiktok.ui.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import com.kyawhut.atsycast.share.base.BaseTvActivityWithVM
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.utils.extension.get
import com.kyawhut.atsycast.share.utils.extension.put
import com.kyawhut.atsycast.share.utils.player.PlayerManager
import com.kyawhut.atsycast.share.utils.player.PlayerManagerImpl
import com.kyawhut.atsycast.tiktok.R
import com.kyawhut.atsycast.tiktok.data.network.response.VideoResponse
import com.kyawhut.atsycast.tiktok.databinding.ActivityTiktokHomeBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author kyawhtut
 * @date 10/4/21
 */
@AndroidEntryPoint
internal class HomeActivity : BaseTvActivityWithVM<ActivityTiktokHomeBinding, HomeViewModel>(),
    PlayerManager {

    override val layoutID: Int
        get() = R.layout.activity_tiktok_home

    override val vm: HomeViewModel by viewModels()

    private val sh: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }

    private var isShowTuto: Boolean
        get() = sh.get("tiktok_tuto", true)
        set(value) = sh.put("tiktok_tuto", value)

    private val playerManger: PlayerManagerImpl by lazy {
        PlayerManagerImpl.Builder(this).apply {
            playerView = vb.tiktokPlayer
            playerStateListener = this@HomeActivity
            playerPlayPause = vb.playPauseView
        }.build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb.showTuto = isShowTuto

        vm.getVideoList(::onVideoResult)
    }

    private fun onVideoResult(result: NetworkResponse<List<VideoResponse>>) {
        when {
            result.isLoading -> {
                vb.isLoading = true
            }
            result.isSuccess -> {
                vb.isLoading = false
                playTiktok(true)
            }
            result.isError -> {
                vb.isLoading = false
            }
        }
    }

    private fun playTiktok(isNext: Boolean) {
        val tiktokVideo = (if (isNext) vm.nextVideo() else vm.previousVideo()) ?: return
        vb.apply {
            authorName = "%s\n@%s".format(
                tiktokVideo.videoAuthor.authorName,
                tiktokVideo.videoAuthor.authorUserName
            )
            videoDescription = tiktokVideo.videoDescription
            authorThumbnail = tiktokVideo.videoAuthor.authorThumbnail.largeThumbnail
            videoThumbnail = tiktokVideo.videoCover
            showThumbnail = true
            executePendingBindings()
        }
        playerManger.setPlayerTitle(vb.authorName ?: "")
            .setPlayerPoster(tiktokVideo.videoCover)
            .setPlayerSource(tiktokVideo.videoURL).play()

        onPlayerPosition(0)
        onPlayerBufferPosition(0)

        vb.tiktokPlayer.useController = false
    }

    override fun toggleLoading(isLoading: Boolean) {
        vb.apply {
            this.isLoading = isLoading
            executePendingBindings()
        }
    }

    override fun hideAll() {
        vb.showThumbnail = false
    }

    override fun onPlayerEnd() {
        if (PreferenceManager.getDefaultSharedPreferences(this)
                .get("pref_tiktok_auto_play", false)
        ) playTiktok(true)
        else playerManger.replay()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP, KeyEvent.KEYCODE_DPAD_LEFT -> playTiktok(false)
            KeyEvent.KEYCODE_DPAD_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT -> playTiktok(true)
            KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_DPAD_CENTER -> playerManger.togglePlay()
        }
        isShowTuto = false
        vb.showTuto = isShowTuto
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        playerManger.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        playerManger.onResume()
        vb.viewNetworkSpeed.onResume()
    }

    override fun onPause() {
        super.onPause()
        playerManger.onPause()
        vb.viewNetworkSpeed.onPause()
    }
}
