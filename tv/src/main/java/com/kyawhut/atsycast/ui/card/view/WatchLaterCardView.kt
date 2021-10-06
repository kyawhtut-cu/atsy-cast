package com.kyawhut.atsycast.ui.card.view

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.view.isVisible
import com.google.gson.Gson
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.databinding.CardHomeWatchLaterBinding
import com.kyawhut.atsycast.share.base.BaseCardView
import com.kyawhut.atsycast.share.db.entity.WatchLaterEntity
import com.kyawhut.atsycast.share.utils.SourceType
import com.kyawhut.atsycast.share.utils.extension.Extension.getDrawableValue
import com.kyawhut.astycast.gsmovie.data.network.response.VideoResponse as GSMovies
import com.kyawhut.atsycast.ets2mm.data.network.response.VideoResponse as ETS2M
import com.kyawhut.atsycast.msubpc.data.network.response.VideoResponse as MSubPc
import com.kyawhut.atsycast.msys.data.network.response.MoviesResponse as MSYS
import com.kyawhut.atsycast.zcm.data.network.response.MoviesResponse as ZCM

/**
 * @author kyawhtut
 * @date 10/5/21
 */
class WatchLaterCardView(context: Context) : BaseCardView<CardHomeWatchLaterBinding>(
    R.layout.card_home_watch_later, context
) {

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        vb.isFocus = selected
    }

    override fun bindUI(data: Any) {
        var _moviesName: String? = ""
        var _moviesPoster: String? = ""
        var _moviesResolutionType: Drawable? = null
        var _moviesResolution: String? = ""
        var _moviesIMDB: String? = ""
        var _moviesYear: String? = ""
        with(data as WatchLaterEntity) {
            when (videoSourceType) {
                is SourceType.MSUB_PC -> {
                    with(Gson().fromJson(this.videoData, MSubPc::class.java)) {
                        _moviesName = videoTitle
                        _moviesPoster = videoCoverImage ?: videoPosterImage
                        _moviesResolutionType = context.getDrawableValue(qualityDrawable)
                        _moviesResolution = videoQuality
                        _moviesIMDB = videoRating
                        _moviesYear = videoYear
                    }
                }
                is SourceType.MSYS -> {
                    with(Gson().fromJson(videoData, MSYS::class.java)) {
                        _moviesName = moviesTitle
                        _moviesPoster = moviesImage
                        _moviesIMDB = if (imdb != null) String.format(
                            "%.1f",
                            imdb!!.toDouble()
                        ) else ""
                        _moviesYear = moviesYear
                    }
                }
                is SourceType.ET2SMM -> {
                    with(Gson().fromJson(videoData, ETS2M::class.java)) {
                        _moviesName = videoTitle
                        _moviesPoster = videoPoster
                        _moviesResolutionType = context.getDrawableValue(qualityDrawable)
                        _moviesResolution = videoQuality
                        _moviesYear = videoYear
                    }
                }
                is SourceType.ZCM -> {
                    with(Gson().fromJson(videoData, ZCM::class.java)) {
                        _moviesName = moviesTitle
                        _moviesPoster = moviesImage
                        _moviesIMDB = if (imdb != null) String.format(
                            "%.1f",
                            imdb!!.toDouble()
                        ) else ""
                        _moviesYear = moviesYear
                    }
                }
                is SourceType.GS_API_SOURCE -> {
                    vb.ivStar.isVisible = false
                    with(Gson().fromJson(videoData, GSMovies.Data::class.java)) {
                        _moviesName = videoTitle
                        _moviesPoster = videoPoster
                        _moviesYear = videoViewCount
                        _moviesIMDB = videoEpisode
                    }
                }
            }
        }

        vb.apply {
            moviesName = _moviesName ?: ""
            moviesPoster = _moviesPoster ?: ""
            if (_moviesResolutionType != null) moviesResolutionType = _moviesResolutionType
            moviesResolution = _moviesResolution ?: ""
            movieIMDB = _moviesIMDB ?: ""
            moviesYear = _moviesYear ?: ""
            executePendingBindings()
        }
    }
}
