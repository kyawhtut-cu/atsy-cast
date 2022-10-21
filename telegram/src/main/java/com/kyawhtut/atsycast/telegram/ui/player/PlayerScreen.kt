package com.kyawhtut.atsycast.telegram.ui.player

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.google.gson.Gson
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.databinding.TelegramPlayerScreenBinding
import com.kyawhtut.atsycast.telegram.ui.exit.ExitDialog.Companion.showExitDialog
import com.kyawhtut.atsycast.telegram.utils.AuthState
import com.kyawhut.atsycast.share.base.BaseTvActivity
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.ui.dialog.PlayerErrorDialog.Companion.showPlayerError
import com.kyawhut.atsycast.share.utils.extension.Extension.screenSize
import com.kyawhut.atsycast.share.utils.extension.startActivity
import com.kyawhut.atsycast.share.utils.player.PlayerManager
import com.kyawhut.atsycast.share.utils.player.PlayerManagerImpl
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File

/**
 * @author kyawhtut
 * @date 21/10/2022
 */
@AndroidEntryPoint
internal class PlayerScreen : BaseTvActivity<TelegramPlayerScreenBinding>(), PlayerManager {

    companion object {
        fun Fragment.openPlayer(chatID: Long, messageID: Long) {
            startActivity<PlayerScreen>(
                PlayerViewModel.EXTRA_CHAT_ID to chatID,
                PlayerViewModel.EXTRA_MESSAGE_ID to messageID
            )
        }
    }

    override val layoutID: Int
        get() = R.layout.telegram_player_screen

    private var path: String = ""
    private val vm: PlayerViewModel by viewModels()

    private var isClicked: Boolean = false
    private var isPlayerControllerShowed = false
    private val playerManger: PlayerManagerImpl by lazy {
        PlayerManagerImpl.Builder(this).apply {
            playerView = vb.playerView
            playerLoadingView = vb.iosLoading
            playerPosterView = vb.playerView.findViewById(R.id.iv_exo_poster)
            playerStateListener = this@PlayerScreen
            playerTitleView = vb.playerView.findViewById(R.id.tv_video_title)
            playerPlayPause = vb.playPauseView
        }.build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vb.apply {
            isShowProgress = true
            playerView.findViewById<DefaultTimeBar>(com.kyawhut.atsycast.share.R.id.exo_progress)
                .setOnKeyListener { _, i, _ ->
                    if (isPlayerControllerShowed) handleProgressBarAction(i)
                    else Handler().postDelayed({
                        isPlayerControllerShowed = true
                    }, 300)
                    i == 23 || i == 66 || i == 19 || i == 20 || i == 21 || i == 22
                }
        }

        vm.setOnLoadingStateListener {
            if (it) toggleLoading(true)
        }

        vm.setOnErrorStateListener {
            onPlayerError(
                it.message,
                Gson().toJson(it)
            )
        }

        vm.setOnAuthStateListener {
            if (it !is AuthState.LoggedIn) {
                finishAffinity()
                return@setOnAuthStateListener
            }
        }

        vm.setOnVideoPathListener {
            path = it.videoSource.url
            play(
                it.videoTitle,
                it.videoThumbnail,
                it.videoSource
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        playerManger.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        playerManger.onRestoreInstanceState(savedInstanceState)
    }

    override fun onPlayerPosition(position: Long) {
        vb.viewProgressPercent.updateLayoutParams {
            width = ((screenSize.x * position) / playerManger.duration).toInt()
        }
    }

    override fun onPlayerBufferPosition(position: Long) {
        vb.viewBufferedPercent.updateLayoutParams {
            width = ((screenSize.x * position) / playerManger.duration).toInt()
        }
    }

    override fun onPlayerControlState(isVisible: Boolean) {
        vb.isShowProgress = !isVisible
        if (isPlayerControllerShowed) isPlayerControllerShowed = isVisible
    }

    override fun toggleLoading(isLoading: Boolean) {
        vb.isLoading = isLoading
    }

    override fun onPlayerEnd() {
        vb.isVideoEnd = true
    }

    override fun onPlayerError(error: String, playerData: String) {
        showPlayerError(
            error,
            playerData
        ) {
            vb.isVideoEnd = false
            playerManger.play(lastPosition = playerManger.position)
        }
    }

    override fun onResume() {
        playerManger.onResume()
        super.onResume()
    }

    override fun onPause() {
        playerManger.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        playerManger.onDestroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (playerManger.onBackPressed()) {
            return
        }
        if (playerManger.isPlaying) playerManger.togglePlay()
        supportFragmentManager.showExitDialog {
            if (it) {
                // delete file here
                val file = File(path)
                if (file.exists()) {
                    file.delete()
                }
                super.onBackPressed()
            } else {
                playerManger.togglePlay()
            }
        }
    }

    private fun play(
        title: String,
        poster: String,
        source: VideoSourceModel,
        lastPosition: Long = 0L,
    ) {
        vb.isVideoEnd = false
        playerManger.setPlayerTitle(title)
            .setPlayerPoster(poster)
            .setPlayerSource(source.url)
            .setPlayerSubtitle(source.subTitle)
            .setPlayerAgent(source.agent)
            .setPlayerCookies(source.cookies)
            .setPlayerCustomHeader(source.customHeader)
            .play(lastPosition = lastPosition)
        vb.playerView.findViewById<DefaultTimeBar>(com.kyawhut.atsycast.share.R.id.exo_progress)
            .requestFocus()
    }

    private fun handleProgressBarAction(action: Int) {
        if (!isClicked) {
            isClicked = true
            when (action) {
                23, 66 -> {
                    // clicked ok key
                    if (playerManger.isEnd) {
                        vb.isVideoEnd = false
                        playerManger.replay()
                    } else playerManger.togglePlay()
                }

                19 -> {
                    // move to up
                    Timber.d("exo_progress key move up")
                    vb.playerView.hideController()
                }

                20 -> {
                    // move to down
                    Timber.d("exo_progress key move down")
                    vb.playerView.hideController()
                }

                21 -> {
                    // move to left
                    Timber.d("exo_progress key move left")
                    vb.isVideoEnd = false
                    playerManger.rewind()
                }

                22 -> {
                    // move to right
                    Timber.d("exo_progress key move right")
                    if (!playerManger.isEnd) {
                        vb.isVideoEnd = false
                        playerManger.fastForward()
                    }
                }
            }
            object : CountDownTimer(300, 1) {
                override fun onTick(millisUntilFinished: Long) {
                    isClicked = true
                }

                override fun onFinish() {
                    isClicked = false
                }
            }.start()
        }

    }
}