package com.kyawhut.atsycast.share.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.adapter.TrackAdapter
import com.kyawhut.atsycast.share.databinding.DialogTrackBinding
import com.kyawhut.atsycast.share.utils.ShareUtils
import com.kyawhut.atsycast.share.utils.extension.putArg
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 10/9/21
 */
class TrackDialog(private val callback: (Int) -> Unit) : DialogFragment() {

    companion object {
        fun FragmentManager.showTrackDialog(
            trackType: TrackType,
            trackList: List<Any>,
            callback: (Int) -> Unit
        ) {
            if (trackList.size == 1 || trackList.isEmpty()) return
            TrackDialog(callback).putArg(
                ShareUtils.EXTRA_TRACK_LIST to trackList,
                ShareUtils.EXTRA_TRACK_TYPE to trackType
            ).show(this, TrackDialog::class.simpleName)
        }
    }

    private lateinit var vb: DialogTrackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTransparent)
    }

    private val trackType: TrackType by lazy {
        arguments?.getSerializable(ShareUtils.EXTRA_TRACK_TYPE) as TrackType
    }

    private val trackList: List<Any> by lazy {
        arguments?.getSerializable(ShareUtils.EXTRA_TRACK_LIST) as List<Any>
    }

    private val trackAdapter: TrackAdapter by lazy {
        TrackAdapter {
            callback(it)
            dismissAllowingStateLoss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("extractTrackList $trackList")
        trackAdapter.setTrackList(trackList)
        vb = DialogTrackBinding.inflate(inflater, container, true)
        vb.apply {
            dialogTitle = getString(
                when (trackType) {
                    TrackType.QUALITY -> R.string.lbl_choose_quality
                    TrackType.SUBTITLES -> R.string.lbl_choose_subtitle
                }
            )
            rvTrackList.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.VERTICAL, false
                )
                adapter = trackAdapter
            }
            executePendingBindings()
        }
        return vb.root
    }

    enum class TrackType {
        QUALITY, SUBTITLES
    }
}
