package com.kyawhtut.atsycast.telegram.ui.photoviewer

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.kyawhtut.atsycast.telegram.R
import com.kyawhtut.atsycast.telegram.databinding.DialogPhotoViewBinding
import com.kyawhtut.atsycast.telegram.ui.player.PlayerScreen.Companion.openPlayer
import com.kyawhut.atsycast.share.utils.binding.ImageBinding.loadImage
import com.kyawhut.atsycast.share.utils.extension.putArg

/**
 * @author kyawhtut
 * @date 21/10/2022
 */
internal class PhotoViewScreen : DialogFragment() {

    companion object {
        private const val EXTRA_PHOTO_PATH = "EXTRA_PHOTO_PATH"
        private const val EXTRA_CAPTION = "EXTRA_CAPTION"
        private const val EXTRA_VIDEO_ID = "EXTRA_VIDEO_ID"
        private const val EXTRA_CHAT_ID = "EXTRA_CHAT_ID"
        private const val EXTRA_MESSAGE_ID = "EXTRA_MESSAGE_ID"

        fun FragmentManager.showPhoto(path: String, caption: String = "") {
            PhotoViewScreen().putArg(
                EXTRA_PHOTO_PATH to path,
                EXTRA_CAPTION to caption
            ).show(this, PhotoViewScreen::class.java.name)
        }

        fun FragmentManager.showVideo(
            chatID: Long,
            messageID: Long,
            thumbnail: String,
            caption: String = "",
            fileID: Int
        ) {
            PhotoViewScreen().putArg(
                EXTRA_CHAT_ID to chatID,
                EXTRA_MESSAGE_ID to messageID,
                EXTRA_PHOTO_PATH to thumbnail,
                EXTRA_CAPTION to caption,
                EXTRA_VIDEO_ID to fileID
            ).show(this, PhotoViewScreen::class.java.name)
        }
    }

    private val photoPath: String by lazy {
        arguments?.getString(EXTRA_PHOTO_PATH) ?: ""
    }
    private val caption: String by lazy {
        arguments?.getString(EXTRA_CAPTION) ?: ""
    }
    private val fileID: Int by lazy {
        arguments?.getInt(EXTRA_VIDEO_ID, -1) ?: -1
    }
    private val chatID: Long by lazy {
        arguments?.getLong(EXTRA_CHAT_ID, 0L) ?: 0L
    }
    private val messageID: Long by lazy {
        arguments?.getLong(EXTRA_MESSAGE_ID, 0L) ?: 0L
    }
    private var vb: DialogPhotoViewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireContext(), theme) {
            override fun onBackPressed() {
                if (vb?.scrollView?.isVisible == true) {
                    vb?.scrollView?.isVisible = false
                    vb?.ivBlurView?.isVisible = false
                    vb?.btnCaption?.isVisible = true
                    vb?.btnPlay?.isVisible = fileID != -1
                    vb?.scrollView?.fullScroll(ScrollView.FOCUS_UP)
                    vb?.btnCaption?.requestFocus()
                } else super.onBackPressed()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = DialogPhotoViewBinding.inflate(inflater, container, true)
        vb?.lifecycleOwner = this
        return vb?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vb?.ivPhoto?.loadImage(photoPath)
        vb?.btnCaption?.isVisible = caption.isNotEmpty()
        vb?.scrollView?.isVisible = false
        vb?.tvCaption?.text = caption
        vb?.ivBlurView?.isVisible = false
        vb?.btnPlay?.isVisible = fileID != -1

        if (fileID == -1) vb?.btnCaption?.requestFocus()
        else vb?.btnPlay?.requestFocus()

        vb?.btnCaption?.setOnClickListener {
            vb?.btnPlay?.isVisible = false
            vb?.btnCaption?.isVisible = false

            vb?.ivBlurView?.isVisible = true
            vb?.scrollView?.isVisible = true
            vb?.scrollView?.requestFocus()
        }

        vb?.btnPlay?.setOnClickListener {
            openPlayer(chatID, messageID)
        }
    }

    override fun onDestroy() {
        vb = null
        super.onDestroy()
    }
}
