package com.kyawhut.atsycast.share.utils.player

import android.content.Context
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.source.TrackGroup
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.model.Track

class TrackSelectionHelper constructor(
    private val context: Context,
    private val trackSelector: DefaultTrackSelector?
) {

    private var mappedTrackInfo: MappingTrackSelector.MappedTrackInfo? = null
    private var selectionOverride: DefaultTrackSelector.SelectionOverride? = null

    private var videoQualityIndex = 0
    private var trackIndex = 0

    private lateinit var trackGroups: TrackGroupArray
    private lateinit var trackGroup: TrackGroup

    var qualityTrackList = mutableListOf<Track>()
        private set

    init {
        if (trackSelector != null) {
            qualityTrackList.clear()
            mappedTrackInfo = trackSelector.currentMappedTrackInfo
            (0 until mappedTrackInfo!!.rendererCount).forEach {
                if (isSupportedRenderer(mappedTrackInfo!!, it)) {
                    videoQualityIndex = it
                    extractVideoTracks()
                }
            }
        }
    }

    private fun extractVideoTracks() {
        trackGroups = mappedTrackInfo?.getTrackGroups(videoQualityIndex) ?: return
        qualityTrackList.add(Track("Auto"))
        (0 until trackGroups.length).forEach {
            trackGroup = trackGroups[it]
            (0 until trackGroup.length).forEach { tIndex ->
                trackIndex = tIndex
                val format = trackGroup.getFormat(trackIndex)
                val trackName = getTrackName(format)
                qualityTrackList.add(Track(trackName, false))
            }
        }
        qualityTrackList.forEachIndexed { index, track ->
            track.isSelected = index == 0
        }
    }

    fun overrideLanguage(lang: String) {
        if (trackSelector == null) return
        val builder = trackSelector.buildUponParameters()
        // clear all the selection values
        builder.clearSelectionOverride(
            C.TRACK_TYPE_TEXT,
            mappedTrackInfo!!.getTrackGroups(C.TRACK_TYPE_TEXT)
        ).setRendererDisabled(C.TRACK_TYPE_TEXT, true)
        if (lang != "close") {
            builder.setPreferredTextLanguage(lang)
                .setSelectUndeterminedTextLanguage(true)
                .setDisabledTextTrackSelectionFlags(C.SELECTION_FLAG_FORCED)
                .setRendererDisabled(C.TRACK_TYPE_TEXT, false)
        }
        trackSelector.parameters = builder.build()
    }

    fun changeQuality(selectedIndex: Int) {
        if (trackSelector == null) return
        val builder = trackSelector.parameters.buildUpon()
        //  clear all the selection values
        builder.clearSelectionOverrides(videoQualityIndex)
        if (selectedIndex != 0) {
            trackIndex = selectedIndex - 1
            selectionOverride = DefaultTrackSelector.SelectionOverride(
                videoQualityIndex, trackIndex
            )
            builder.setSelectionOverride(videoQualityIndex, trackGroups, selectionOverride)
        }
        trackSelector.parameters = builder.build()
        qualityTrackList.forEachIndexed { index, track ->
            track.isSelected = index == selectedIndex
        }
    }

    private fun getTrackName(format: Format) =
        buildResolutionString(format) + " (" + buildBitrateString(format) + ")"

    private fun buildResolutionString(format: Format): String {
        val width = format.width
        val height = format.height
        return if (width == Format.NO_VALUE || height == Format.NO_VALUE)
            buildHardCodedResolutionString(format)
        else context.getString(R.string.exo_track_resolution, width, height)
    }

    private fun buildHardCodedResolutionString(format: Format): String {
        return when (format.bitrate) {
            314572 -> context.getString(R.string.exo_track_resolution, 160, 120)
            419430 -> context.getString(R.string.exo_track_resolution, 256, 144)
            524288 -> context.getString(R.string.exo_track_resolution, 426, 240)
            1572864 -> context.getString(R.string.exo_track_resolution, 640, 360)
            5242880 -> context.getString(R.string.exo_track_resolution, 1280, 720)
            else -> ""
        }
    }

    private fun buildBitrateString(format: Format): String {
        val bitrate = format.bitrate
        return if (bitrate == Format.NO_VALUE) "" else context.getString(
            R.string.exo_track_bitrate,
            bitrate / 1000000f
        )
    }

    private fun isSupportedRenderer(
        mappedTrackInfo: MappingTrackSelector.MappedTrackInfo,
        rendererIndex: Int,
    ): Boolean {
        val trackGroupArray = mappedTrackInfo.getTrackGroups(rendererIndex)
        if (trackGroupArray.length == 0) {
            return false
        }
        val trackType = mappedTrackInfo.getRendererType(rendererIndex)
        return isSupportedTrackType(trackType)
    }

    private fun isSupportedTrackType(trackType: Int): Boolean {
        return when (trackType) {
            C.TRACK_TYPE_VIDEO -> true
            else -> false
        }
    }

}
