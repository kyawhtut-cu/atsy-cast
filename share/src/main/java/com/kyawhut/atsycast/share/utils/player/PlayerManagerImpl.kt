package com.kyawhut.atsycast.share.utils.player

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.RowHeaderView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.kyawhut.atsycast.share.components.IOSLoading
import com.kyawhut.atsycast.share.components.PlayPauseView
import com.kyawhut.atsycast.share.network.utils.UnsafeOkHttpClient
import com.kyawhut.atsycast.share.utils.binding.ImageBinding.loadImage
import com.kyawhut.atsycast.share.utils.binding.TextViewBinding.applyMMFont
import timber.log.Timber
import kotlin.math.max
import kotlin.math.min

/**
 * @author kyawhtut
 * @date 9/1/21
 */
class PlayerManagerImpl private constructor(
    private val activity: FragmentActivity,
    private val playerView: PlayerView,
    private val playerLoadingView: IOSLoading?,
    private val playerPlayPause: PlayPauseView?,
    private val playerPosterView: ImageView?,
    private val playerTitleView: RowHeaderView?,
    private val playerStateListener: PlayerManager? = null
) {

    companion object {
        private const val PLAYER_POSITION = "xPlayer.POSITION"

        private const val DEFAULT_SEEK = 15000L

        const val PLAYER_PLAY_PAUSE_ANIMATION = 1000L
    }

    private var playerSource: String = ""
    private var playerAgent: String = Util.getUserAgent(
        activity,
        activity.applicationInfo.loadLabel(activity.packageManager).toString()
    )
    private var playerCookies: String = ""
    private var playerCustomHeader: MutableList<Pair<String, String>> = mutableListOf()
    private var playerTitle: String = ""
    private var playerDescription: String = ""
    private var playerPoster: String = ""

    private var _playbackPosition: Long = 0
    private var _currResizeMode: EnumResizeMode = EnumResizeMode.FILL
    val duration: Long
        get() = _simpleExoPlayer.duration
    val position: Long
        get() = _simpleExoPlayer.currentPosition
    private val _bandwidthMeter by lazy {
        DefaultBandwidthMeter.Builder(activity).build()
    }
    private val _simpleExoPlayer: SimpleExoPlayer by lazy {
        SimpleExoPlayer.Builder(
            activity,
            DefaultRenderersFactory(activity)
        ).setTrackSelector(
            DefaultTrackSelector(
                activity,
                AdaptiveTrackSelection.Factory()
            )
        ).setLoadControl(DefaultLoadControl())
            .setBandwidthMeter(_bandwidthMeter)
            .build()
    }
    private val componentListener: ComponentListener by lazy {
        ComponentListener(::onPlayerStateChanged, ::onPlayerError)
    }

    private val playerPositionCountDown: CountDownTimer by lazy {
        object : CountDownTimer(100, 1) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                playerPositionCountDown.cancel()
                playerPositionCountDown.start()
                playerStateListener?.onPlayerPosition(_simpleExoPlayer.currentPosition)
                playerStateListener?.onPlayerBufferPosition(_simpleExoPlayer.bufferedPosition)
            }
        }
    }

    private val playPauseAnimation by lazy {
        object : CountDownTimer(PLAYER_PLAY_PAUSE_ANIMATION, 1) {
            override fun onTick(p0: Long) {
                playerPlayPause?.alpha = p0 / (PLAYER_PLAY_PAUSE_ANIMATION * 1f)
            }

            override fun onFinish() {
                playerPlayPause?.alpha = if (_simpleExoPlayer.playWhenReady ||
                    _simpleExoPlayer.playbackState == Player.STATE_BUFFERING
                ) 0f else 1f
            }
        }
    }

    val isEnd: Boolean
        get() = _simpleExoPlayer.playbackState == Player.STATE_ENDED

    init {
        _simpleExoPlayer.apply {
            addListener(componentListener)
            playWhenReady = true
        }

        playerView.apply {
            keepScreenOn = true
            player = _simpleExoPlayer
            useController = true
            hideController()
        }

        playerView.setControllerVisibilityListener {
            playerStateListener?.onPlayerControlState(it == View.VISIBLE)
        }
    }

    fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putLong(PLAYER_POSITION, _simpleExoPlayer.currentPosition)
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        _playbackPosition = savedInstanceState.getLong(PLAYER_POSITION)
    }

    fun setPlayerTitle(playerTitle: String): PlayerManagerImpl {
        this.playerTitle = playerTitle
        playerTitleView?.apply {
            applyMMFont(true)
            text = this@PlayerManagerImpl.playerTitle
        }
        return this
    }

    fun setPlayerPoster(
        playerPoster: String,
        isNeedToCache: Boolean = true,
        isBlur: Boolean = false
    ): PlayerManagerImpl {
        this.playerPoster = playerPoster
        playerPosterView?.loadImage(
            this.playerPoster,
            isBlur = isBlur,
            isNeedToCache = isNeedToCache
        )
        if (!isNeedToCache) showPoster()
        return this
    }

    fun setPlayerSource(playerSource: String): PlayerManagerImpl {
        Timber.d("URL => $playerSource")
        this.playerSource = playerSource
        return this
    }

    fun setPlayerAgent(playerAgent: String?): PlayerManagerImpl {
        if (playerAgent.isNullOrEmpty()) return this
        this.playerAgent = playerAgent
        return this
    }

    fun setPlayerCookies(playerCookies: String?): PlayerManagerImpl {
        this.playerCookies = playerCookies ?: ""
        return this
    }

    fun setPlayerCustomHeader(playerCustomerHeader: List<Pair<String, String>>): PlayerManagerImpl {
        this.playerCustomHeader = playerCustomerHeader.toMutableList()
        return this
    }

    fun addPlayerCustomHeader(customHeader: Pair<String, String>): PlayerManagerImpl {
        this.playerCustomHeader.add(customHeader)
        return this
    }

    fun replay() {
        _playbackPosition = _simpleExoPlayer.currentPosition
        _simpleExoPlayer.seekTo(
            _simpleExoPlayer.currentWindowIndex,
            C.TIME_UNSET
        )
        _simpleExoPlayer.playWhenReady = true
        playerPlayPause?.toPlay()
    }

    fun togglePlay() {
        _simpleExoPlayer.playWhenReady = !_simpleExoPlayer.playWhenReady
        playPauseAnimation.cancel()
        playerPositionCountDown.cancel()
        when {
            _simpleExoPlayer.playWhenReady -> {
                playPauseAnimation.start()
                playerPositionCountDown.start()
            }
            _simpleExoPlayer.playbackState == Player.STATE_BUFFERING -> playerPlayPause?.alpha = 0f
            else -> playerPlayPause?.alpha = 1f
        }
        playerPlayPause?.toggle(_simpleExoPlayer.playWhenReady)
    }

    fun rewind() {
        if (_simpleExoPlayer.isCurrentWindowSeekable) {
            seekToOffset(-DEFAULT_SEEK)
        }
    }

    fun fastForward() {
        if (_simpleExoPlayer.playbackState == Player.STATE_ENDED) return
        if (_simpleExoPlayer.isCurrentWindowSeekable) {
            seekToOffset(DEFAULT_SEEK)
        }
    }

    fun play(source: String = this.playerSource, lastPosition: Long = 0L) {
        if (source.isEmpty()) {
            onPlayerError("Player source must not be null.")
            return
        }

        this.playerSource = source

        playerPlayPause?.toPlay()

        val okHttpDataSource: DataSource.Factory = OkHttpDataSource.Factory(
            UnsafeOkHttpClient.getUnsafeOkHttpClient().build()
        ).apply {
            setUserAgent(
                Util.getUserAgent(
                    activity,
                    playerAgent
                )
            )
            if (playerCookies.isNotEmpty())
                setDefaultRequestProperties(mapOf("Cookie" to playerCookies))
            playerCustomHeader.forEach {
                setDefaultRequestProperties(mapOf(it.first to it.second))
            }
        }
        val dataSourceFactory = if (playerCookies.isNotEmpty() || playerCustomHeader.isNotEmpty()) {
            val httpDataSourceFactory = DefaultHttpDataSource.Factory().apply {
                setUserAgent(playerAgent)
                if (playerCookies != null) setDefaultRequestProperties(mapOf("Cookie" to playerCookies))
                playerCustomHeader.forEach {
                    setDefaultRequestProperties(mapOf(it.first to it.second))
                }
            }
            DefaultDataSourceFactory(
                activity.applicationContext,
                null,
                httpDataSourceFactory
            )
        } else {
            DefaultDataSourceFactory(
                activity,
                playerAgent
            )
        }

        val isDrive = playerSource.contains("drive.google.com", true)

        when (Util.inferContentType(Uri.parse(playerSource))) {
            C.TYPE_HLS -> {
                val mediaSource = HlsMediaSource.Factory(dataSourceFactory.takeIf { isDrive }
                    ?: okHttpDataSource).createMediaSource(getURL(playerSource))
                _simpleExoPlayer.setMediaSource(mediaSource)
            }
            C.TYPE_OTHER -> {
                val mediaSource = if (playerSource.contains("rtmp", true)) {
                    ProgressiveMediaSource.Factory(RtmpDataSourceFactory())
                        .createMediaSource(getURL(playerSource))
                } else {
                    ProgressiveMediaSource.Factory(dataSourceFactory.takeIf { isDrive }
                        ?: okHttpDataSource)
                        .createMediaSource(getURL(playerSource))
                }
                _simpleExoPlayer.setMediaSource(mediaSource)
            }
            C.TYPE_DASH -> {
                val mediaSource = DashMediaSource.Factory(dataSourceFactory.takeIf { isDrive }
                    ?: okHttpDataSource)
                    .createMediaSource(getURL(playerSource))
                _simpleExoPlayer.setMediaSource(mediaSource)
            }
            else -> {
                onPlayerError("Unknown video format.")
            }
        }

        _simpleExoPlayer.prepare()

        val mediaSession = MediaSessionCompat(activity, activity.packageName)
        val mediaSessionConnector = MediaSessionConnector(mediaSession)
        mediaSessionConnector.setPlayer(_simpleExoPlayer)
        mediaSession.isActive = true

        _playbackPosition = lastPosition
        onResume()

        showPoster()
    }

    fun onResume() {
        if (_playbackPosition > 0L) {
            _simpleExoPlayer.seekTo(_playbackPosition)
        }

        _simpleExoPlayer.playWhenReady = true
    }

    fun onPause() {
        _playbackPosition = _simpleExoPlayer.currentPosition
        _simpleExoPlayer.playWhenReady = false
    }

    fun onDestroy() {
        playerPositionCountDown.cancel()
        playerStateListener?.onPlayerDestroy(
            _simpleExoPlayer.duration,
            _simpleExoPlayer.currentPosition
        )
        playerView.player = null
        _simpleExoPlayer.release()
    }

    fun onBackPressed(): Boolean {
        return if (playerView.isControllerVisible) {
            playerView.hideController()
            true
        } else false
    }

    private fun seekToOffset(offsetMs: Long) {
        var positionMs = _simpleExoPlayer.currentPosition + offsetMs
        val durationMs = _simpleExoPlayer.duration

        if (durationMs != C.TIME_UNSET) {
            positionMs = min(positionMs, durationMs)
        }

        positionMs = max(positionMs, 0)
        _simpleExoPlayer.seekTo(_simpleExoPlayer.currentWindowIndex, positionMs)
    }

    private fun getURL(url: String) = MediaItem.fromUri(Uri.parse(url))

    private fun hideAllUI() {
        playerPlayPause?.alpha = 0f
        playerStateListener?.hideAll()
        toggleLoading(false)
        playerPosterView?.isVisible = false
    }

    private fun showPoster() {
        playerStateListener?.showPoster()
        playerPosterView?.isVisible = true
    }

    private fun toggleLoading(isLoading: Boolean) {
        playerStateListener?.toggleLoading(isLoading)
        playerLoadingView?.toggleAnimation(isLoading)
    }

    private fun onPlayerEnd() {
        playerStateListener?.onPlayerEnd()
    }

    private fun getCustomerHeaderString(): String {
        var header = ""
        playerCustomHeader.forEachIndexed { index, (key, value) ->
            header += """
                    {
                        $key: $value
                    }
            """.trimIndent()
        }
        return header
    }

    private fun onPlayerError(error: String) {
        playerStateListener?.onPlayerError(
            error,
            """
🔴 $playerTitle <strong>failure</strong>

<strong>Player URL</strong> => $playerSource%s%s%s
            """.format(
                if (playerAgent.isNotEmpty()) """ 
                    
<strong>Player Agent</strong> => $playerAgent
                """.trimIndent() else "",
                if (playerCookies.isNotEmpty()) """ 
                    
<strong>Player Cookies</strong> => $playerCookies
                """.trimIndent() else "",
                if (playerCustomHeader.isNotEmpty()) """ 
                    
<strong>Player CustomHeader</strong> => [
                ${
                    playerCustomHeader.joinToString("\n") { (key, value) ->
                        """
    {
        $key: $value
    }
    """.trimIndent()
                    }
                }
]
                """.trimIndent() else ""
            ).trimIndent()
        )
    }

    private fun onPlayerStateChanged(playbackState: Int) {
        val state: String
        when (playbackState) {
            Player.STATE_IDLE -> {
                state = "ExoPlayer.STATE_IDLE      -"
            }
            Player.STATE_BUFFERING -> {
                playerPositionCountDown.cancel()
                hideAllUI()
                toggleLoading(true)
                state = "ExoPlayer.STATE_BUFFERING -"
            }
            Player.STATE_READY -> {
                playerPositionCountDown.start()
                hideAllUI()
                state = "ExoPlayer.STATE_READY     -"
                playerPlayPause?.alpha = if (_simpleExoPlayer.playWhenReady) 0f else 1f
            }
            Player.STATE_ENDED -> {
                state = "ExoPlayer.STATE_ENDED     -"
                hideAllUI()
                onPlayerEnd()
                playerPositionCountDown.cancel()
            }
            else -> {
                state = "UNKNOWN_STATE             -"
            }
        }
        Timber.d(state)
    }

    private fun onPlayerError(error: ExoPlaybackException) {
        playerPositionCountDown.cancel()
        toggleLoading(false)
        onPlayerError(error.message ?: "Something went wrong.")
    }

    class Builder(private val activity: FragmentActivity) {

        var playerView: PlayerView? = null
        var playerLoadingView: IOSLoading? = null
        var playerPlayPause: PlayPauseView? = null
        var playerPosterView: ImageView? = null
        var playerTitleView: RowHeaderView? = null
        var playerStateListener: PlayerManager? = null

        fun build(): PlayerManagerImpl {
            if (playerView == null)
                throw RuntimeException("Views must not be null.")
            return PlayerManagerImpl(
                activity,
                playerView!!,
                playerLoadingView,
                playerPlayPause,
                playerPosterView,
                playerTitleView,
                playerStateListener,
            )
        }
    }
}
