package com.kyawhut.atsycast.gs_mm.ui.player

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.activity.viewModels
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.HeaderItem
import androidx.leanback.widget.ListRow
import com.kyawhut.atsycast.gs_mm.data.network.response.EpisodeResponse
import com.kyawhut.atsycast.gs_mm.ui.card.CardPresenter
import com.kyawhut.atsycast.gs_mm.ui.card.view.EpisodeCardView
import com.kyawhut.atsycast.share.base.BasePlayerActivity
import com.kyawhut.atsycast.share.model.VideoSourceModel
import com.kyawhut.atsycast.share.utils.ShareUtils
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@AndroidEntryPoint
class PlayerActivity : BasePlayerActivity() {

    private val vm: PlayerViewModel by viewModels()
    override val appName: String
        get() = vm.appName
    override val channelLogo: String
        get() = vm.channelLogo

    override fun isRelatedView(view: View): Boolean {
        return view is EpisodeCardView
    }

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (vm.videoSource == null) {
            finishAndRemoveTask()
            return
        }

        playVideo()

        if (vm.episodeList.isNotEmpty())
            setEpisode(
                ListRow(
                    HeaderItem(1L, "Related Episodes"),
                    ArrayObjectAdapter(CardPresenter(this)).apply {
                        setItems(vm.episodeList, EpisodeResponse.Data.diff)
                    }
                )
            )
    }

    override fun onRelatedItemClicked(item: Any) {
        vm.updatePlayerPositionCache(lastPosition, duration)
        if (item is EpisodeResponse.Data) {
            val originTitle = vm.videoTitle.split(" -")[0]
            vm.videoID = item.episodeID
            vm.videoTitle = "%s - %s".format(
                originTitle, item.episodeTitle
            )
            vm.videoSource = VideoSourceModel(
                item.episodeID,
                item.episodeTitle,
                null,
                item.episodeURL,
                sourceType = ShareUtils.PLAYER_M3U8_EXTENSION,
                subTitle = item.episodeSubtitle
            )
            vm.isResume = true
            playVideo(item.episodeCover)
        }
    }

    private fun playVideo(cover: String = vm.videoCover) {
        play(vm.videoTitle, vm.videoCover, vm.videoSource!!, vm.lastPosition)
    }

    override fun onPlayerPosition(position: Long) {
        if (countDownTimer == null) {
            countDownTimer = object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    vm.updatePlayerPositionCache(lastPosition, duration)
                    countDownTimer?.start()
                }
            }
            countDownTimer!!.start()
        }
        super.onPlayerPosition(position)
    }

    override fun onPlayerEnd() {
        countDownTimer?.cancel()
        vm.updatePlayerPositionCache(lastPosition, duration)
        super.onPlayerEnd()
    }

    override fun onPlayerError(error: String, playerData: String) {
        countDownTimer?.cancel()
        vm.updatePlayerPositionCache(lastPosition, duration)
        super.onPlayerError(error, playerData)
    }

    override fun onDestroy() {
        countDownTimer?.cancel()
        vm.updatePlayerPositionCache(lastPosition, duration)
        super.onDestroy()
    }
}
