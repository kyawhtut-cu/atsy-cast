package com.kyawhut.atsycast.free2air.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.*
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.upstream.*
import com.kyawhut.atsycast.free2air.R
import com.kyawhut.atsycast.free2air.data.network.response.Free2AirResponse
import com.kyawhut.atsycast.free2air.databinding.ActivityF2aHomeBinding
import com.kyawhut.atsycast.free2air.ui.card.CardPresenter
import com.kyawhut.atsycast.share.base.BaseTvActivityWithVM
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import com.kyawhut.atsycast.share.utils.ToggleBackground
import com.kyawhut.atsycast.share.utils.player.PlayerManager
import com.kyawhut.atsycast.share.utils.player.PlayerManagerImpl
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 8/31/21
 */
@AndroidEntryPoint
internal class HomeActivity : BaseTvActivityWithVM<ActivityF2aHomeBinding, HomeViewModel>(),
    PlayerManager {

    private val toggleBackground: ToggleBackground by lazy {
        ToggleBackground()
    }

    override val layoutID: Int
        get() = R.layout.activity_f2a_home

    override val vm: HomeViewModel by viewModels()

    private var isDoubleBackToExitPressedOnce = false
    private val playerManger: PlayerManagerImpl by lazy {
        PlayerManagerImpl.Builder(this).apply {
            playerView = vb.playerView
            playerLoadingView = vb.loadingView
            playerPosterView = vb.playerView.findViewById(R.id.iv_exo_poster)
            playerStateListener = this@HomeActivity
        }.build()
    }

    private val channelAdapter: ArrayObjectAdapter by lazy {
        ArrayObjectAdapter(ListRowPresenter(FocusHighlight.ZOOM_FACTOR_MEDIUM))
    }
    private var f2aChannelFragment: RowsSupportFragment? = null
    private var playingIndex: Int = 0
    private var playingIndexRow: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initF2AChannel()

        vb.appName = vm.appName

        vb.playerState.onClickListener = {
            // fixme: need to add api fail retry logic
            if (channelAdapter.size() == 0) {
                vm.getFree2Air(::onF2ANetworkState)
            } else playerManger.play()
        }

        toggleBackground.callBack = {
            playerManger.setPlayerPoster(it, isBlur = true, isNeedToCache = false)
        }

        vm.getFree2Air(::onF2ANetworkState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        playerManger.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        playerManger.onRestoreInstanceState(savedInstanceState)
    }

    private fun onF2ANetworkState(state: NetworkResponse<HashMap<String, List<Free2AirResponse.Data>>>) {
        vb.loadingView.toggleAnimation(state.isLoading)
        toggleLoading(state.isLoading)
        when {
            state.isLoading -> {
                vb.playerView.useController = false
            }
            state.isSuccess -> {
                vb.playerView.useController = true
                channelAdapter.clear()
                var rowIndex = 1L
                state.data?.forEach {
                    if (rowIndex == 1L) {
                        play(
                            it.value.first().channelName,
                            it.value.first().channelCover,
                            it.value.first().channelStream
                        )
                    }
                    channelAdapter.add(
                        ListRow(
                            HeaderItem(rowIndex++, it.key),
                            ArrayObjectAdapter(CardPresenter(this)).apply {
                                setItems(
                                    it.value.mapIndexed { index, free2AirResponse ->
                                        free2AirResponse.apply {
                                            isPlaying = index == 0 && rowIndex == 2L
                                        }
                                    },
                                    Free2AirResponse.diff
                                )
                            }
                        )
                    )
                }
            }
            state.isError -> {
                vb.playerState.apply {
                    actionText = "ပြန်လည်ကြိုးစားမည်"
                    message = state.error?.message ?: "Internet connection error."
                }
            }
        }
    }

    private fun initF2AChannel() {
        f2aChannelFragment = supportFragmentManager.findFragmentById(
            R.id.channel_fragment
        ) as RowsSupportFragment?
        if (f2aChannelFragment == null) {
            f2aChannelFragment = RowsSupportFragment()
            supportFragmentManager.commit {
                replace(R.id.channel_fragment, f2aChannelFragment!!)
            }
        }

        f2aChannelFragment?.adapter = channelAdapter

        f2aChannelFragment?.setOnItemViewClickedListener { _, item, _, row ->
            if (channelAdapter[playingIndexRow] is ListRow) {
                ((channelAdapter[playingIndexRow] as ListRow).adapter as ArrayObjectAdapter?)?.let {
                    it.replace(playingIndex, (it.get(playingIndex) as Free2AirResponse.Data).apply {
                        isPlaying = false
                    })
                }
            }
            if (row is ListRow) {
                playingIndexRow = channelAdapter.indexOf(row)
                (row.adapter as ArrayObjectAdapter?)?.let {
                    playingIndex = it.indexOf(item)
                    if (playingIndex != -1) {
                        it.replace(playingIndex, (item as Free2AirResponse.Data).apply {
                            isPlaying = true
                        })
                        play(item.channelName, item.channelCover, item.channelStream)
                    }
                }
            }
        }
    }

    private fun play(title: String, poster: String, source: String) {
        vb.playerState.hide()
        vb.appName = title
        vb.showLive = true
        playerManger.setPlayerTitle(title)
            .setPlayerPoster(poster)
            .setPlayerSource(source)
            .play()
        toggleBackground.stop()
    }

    override fun toggleLoading(isLoading: Boolean) {
        vb.viewLoading.isVisible = isLoading
    }

    override fun onPlayerEnd() {
        vb.playerState.apply {
            actionText = "ပြန်ကြည့်မည်"
            message = "Video ကြည့်ရှုခြင်း ပြီးဆုံးသွားပါပြီ။"
        }
    }

    override fun onPlayerError(error: String, playerData: String) {
        vb.playerState.apply {
            actionText = "ပြန်လည်ကြိုးစားမည်"
            message = error
        }
        Timber.d(playerData)
        TelegramHelper.sendLog(playerData)
    }

    override fun onResume() {
        super.onResume()
        vb.networkSpeed.onResume()
        toggleBackground.start()
        playerManger.onResume()
    }

    override fun onPause() {
        vb.networkSpeed.onPause()
        playerManger.onPause()
        toggleBackground.stop()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        toggleBackground.stop()
        playerManger.onDestroy()
    }

    override fun onBackPressed() {
        if (playerManger.onBackPressed()) return
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
