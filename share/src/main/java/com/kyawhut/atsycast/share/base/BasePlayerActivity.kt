package com.kyawhut.atsycast.share.base

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.PersistableBundle
import android.view.KeyEvent
import android.view.View
import androidx.annotation.CallSuper
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.commit
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.FocusHighlight
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.ListRowPresenter
import com.google.android.exoplayer2.ui.DefaultTimeBar
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.databinding.AtsyPlayerBinding
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.ui.dialog.PlayerErrorDialog.Companion.showPlayerError
import com.kyawhut.atsycast.share.utils.color.RandomColor
import com.kyawhut.atsycast.share.utils.extension.Extension.screenSize
import com.kyawhut.atsycast.share.utils.player.PlayerManager
import com.kyawhut.atsycast.share.utils.player.PlayerManagerImpl
import es.dmoral.toasty.Toasty
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 9/8/21
 */
abstract class BasePlayerActivity : BaseTvActivity<AtsyPlayerBinding>(), PlayerManager {

    open val episodeFocusHighlight: Int = FocusHighlight.ZOOM_FACTOR_MEDIUM

    override val layoutID: Int
        get() = R.layout.atsy_player
    private var isDoubleBackToExitPressedOnce = false
    val duration: Long
        get() = playerManger.duration
    val lastPosition: Long
        get() = playerManger.position

    private var isClicked: Boolean = false
    private var isPlayerControllerShowed = false

    private val playerManger: PlayerManagerImpl by lazy {
        PlayerManagerImpl.Builder(this).apply {
            playerView = vb.playerView
            playerLoadingView = vb.iosLoading
            playerPosterView = vb.playerView.findViewById(R.id.iv_exo_poster)
            playerStateListener = this@BasePlayerActivity
            playerTitleView = vb.playerView.findViewById(R.id.tv_video_title)
            playerPlayPause = vb.playPauseView
        }.build()
    }
    private val episodeAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(ListRowPresenter(episodeFocusHighlight))
    }
    private var episodeFragment: RowsSupportFragment? = null
    private var focusRowCount: Int = -1
    private var numberOfRowCount: Int = 0
    open fun isRelatedView(view: View): Boolean {
        return false
    }

    open fun onRelatedItemClicked(item: Any) {}

    open val appName: String = ""

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEpisode()

        vb.apply {
            isShowProgress = true
            appName = this@BasePlayerActivity.appName
            viewAppName.setBackgroundColor(RandomColor().randomColor())
            playerView.findViewById<DefaultTimeBar>(R.id.exo_progress)
                .setOnKeyListener { _, i, _ ->
                    if (isPlayerControllerShowed) handleProgressBarAction(i)
                    else Handler().postDelayed({
                        isPlayerControllerShowed = true
                    }, 300)
                    i != 4
                }
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

    private fun initEpisode() {
        episodeFragment = supportFragmentManager.findFragmentById(
            R.id.view_episode
        ) as RowsSupportFragment?
        if (episodeFragment == null) {
            episodeFragment = RowsSupportFragment()
            supportFragmentManager.commit {
                replace(R.id.view_episode, episodeFragment!!)
            }
        }

        episodeFragment?.adapter = episodeAdapter

        episodeFragment?.setOnItemViewClickedListener { _, item, _, row ->
            onRelatedItemClicked(item)
            vb.focusEpisode = false
            changeRelatedHeight()
        }
    }

    fun play(
        title: String,
        poster: String,
        source: VideoSourceModel,
        lastPosition: Long = 0L
    ) {
        vb.isVideoEnd = false
        playerManger.setPlayerTitle(title)
            .setPlayerPoster(poster)
            .setPlayerSource(source.url)
            .setPlayerAgent(source.agent)
            .setPlayerCookies(source.cookies)
            .setPlayerCustomHeader(source.customHeader)
            .play(lastPosition = lastPosition)
        vb.playerView.findViewById<DefaultTimeBar>(R.id.exo_progress).requestFocus()
    }

    fun setEpisode(rows: ListRow) {
        numberOfRowCount += 1
        episodeAdapter.add(rows)
    }

    @CallSuper
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

    @CallSuper
    override fun onPlayerControlState(isVisible: Boolean) {
        vb.isShowProgress = !isVisible
        if (isPlayerControllerShowed) isPlayerControllerShowed = isVisible
        vb.showEpisode = if (currentFocus == null) isVisible
        else isRelatedView(currentFocus!!) || isVisible
    }

    @CallSuper
    override fun toggleLoading(isLoading: Boolean) {
        vb.isLoading = isLoading
    }

    @CallSuper
    override fun onPlayerEnd() {
        vb.isVideoEnd = true
    }

    @CallSuper
    override fun onPlayerError(error: String, playerData: String) {
        showPlayerError(
            error,
            playerData
        ) {
            vb.isVideoEnd = false
            playerManger.play(lastPosition = playerManger.position)
        }
    }

    @CallSuper
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        handleKeyEvent(keyCode)
        return super.onKeyDown(keyCode, event)
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
                    changeRelatedHeight()
                }
                19 -> {
                    // move to up
                    Timber.d("exo_progress key move up")
                    changeRelatedHeight()
                }
                20 -> {
                    // move to up
                    Timber.d("exo_progress key move down")
                    if (numberOfRowCount > 0) {
                        vb.focusEpisode = true
                        vb.viewEpisode.requestFocus()
                        vb.bottomDetail.setGuidelinePercent(0.5f)
                        vb.playerView.hideController()
                    } else changeRelatedHeight()
                }
                21 -> {
                    // move to left
                    Timber.d("exo_progress key move left")
                    vb.isVideoEnd = false
                    playerManger.rewind()
                    changeRelatedHeight()
                }
                22 -> {
                    // move to right
                    Timber.d("exo_progress key move right")
                    if (!playerManger.isEnd) {
                        vb.isVideoEnd = false
                        playerManger.fastForward()
                    }
                    changeRelatedHeight()
                }
                4 -> {
                    // back handle
                    onBackPressed()
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

    private fun changeRelatedHeight() {
        if ((vb.bottomDetail.layoutParams as ConstraintLayout.LayoutParams).guidePercent == 0.75f) return
        vb.focusEpisode = false
        focusRowCount = 0
        vb.bottomDetail.setGuidelinePercent(0.75f)
    }

    private fun handleKeyEvent(keyCode: Int) {
        if (keyCode == 20 || keyCode == 19) {
            currentFocus?.let {
                val percent = if (keyCode == 20) {
                    if (numberOfRowCount > 1) {
                        focusRowCount += 1
                        if (focusRowCount >= numberOfRowCount) {
                            focusRowCount = numberOfRowCount - 1
                        }
                    }
                    when {
                        isRelatedView(it) -> {
                            0.5f
                        }
                        else -> 0.75f
                    }
                } else {
                    focusRowCount -= 1
                    if (focusRowCount <= -1) focusRowCount = -1
                    when {
                        isRelatedView(it) -> {
                            when (focusRowCount) {
                                0 -> 0.5f
                                -1 -> 0.75f
                                else -> 0.5f
                            }
                        }
                        else -> 0.7f
                    }
                }
                vb.bottomDetail.setGuidelinePercent(percent)
                if (percent == 0.75f) {
                    vb.focusEpisode = false
                    vb.playerView.showController()
                    vb.playerView.findViewById<DefaultTimeBar>(R.id.exo_progress).requestFocus()
                }
            }
        }
    }

    @CallSuper
    override fun onResume() {
        vb.viewNetworkSpeed.onResume()
        playerManger.onResume()
        super.onResume()
    }

    @CallSuper
    override fun onPause() {
        vb.viewNetworkSpeed.onPause()
        playerManger.onPause()
        super.onPause()
    }

    @CallSuper
    override fun onDestroy() {
        playerManger.onDestroy()
        super.onDestroy()
    }

    @CallSuper
    override fun onBackPressed() {
        if (playerManger.onBackPressed() || vb.showEpisode == true) {
            changeRelatedHeight()
            vb.showEpisode = false
            return
        }
        if (isDoubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        isDoubleBackToExitPressedOnce = true
        Toasty.warning(this, getString(R.string.lbl_on_back_pressed), Toasty.LENGTH_LONG).show()

        Handler().postDelayed(
            {
                isDoubleBackToExitPressedOnce = false
            }, 2000
        )
    }
}
