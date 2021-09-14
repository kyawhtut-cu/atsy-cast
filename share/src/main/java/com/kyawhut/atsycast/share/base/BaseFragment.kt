package com.kyawhut.atsycast.share.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * @author kyawhtut
 * @date 9/3/21
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    abstract val layoutID: Int

    protected lateinit var vb: VB
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = DataBindingUtil.inflate(inflater, layoutID, container, false)
        return vb.root
    }
}
