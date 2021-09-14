package com.kyawhut.atsycast.share.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.kyawhut.atsycast.share.R
import com.kyawhut.atsycast.share.databinding.DialogLoadingBinding

/**
 * @author kyawhtut
 * @date 9/14/21
 */
class LoadingDialog(private val fm: FragmentManager) : DialogFragment() {

    private lateinit var vb: DialogLoadingBinding
    override fun isCancelable(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogTransparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        vb = DialogLoadingBinding.inflate(inflater, container, true)
        return vb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb.iosLoading.toggleAnimation(true)
    }

    fun show() {
        show(fm, this::class.java.name)
    }

    fun hide() {
        vb.iosLoading.toggleAnimation(false)
        dismissAllowingStateLoss()
    }
}
