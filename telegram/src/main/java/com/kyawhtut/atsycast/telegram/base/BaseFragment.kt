package com.kyawhtut.atsycast.telegram.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

internal abstract class BaseFragment<VB : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : Fragment() {

    private var _vb: VB? = null

    protected val vb: VB
        get() = _vb!!

    open fun onCreateView(vb: VB, savedInstanceState: Bundle?) {}
    open fun onViewCreated(vb: VB) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vb = DataBindingUtil.inflate(
            inflater,
            layoutRes,
            container,
            false
        )
        _vb?.lifecycleOwner = this

        onCreateView(vb, savedInstanceState)

        _vb?.executePendingBindings()

        return _vb?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onViewCreated(vb)
    }

    override fun onDestroy() {
        super.onDestroy()
        _vb = null
    }
}
