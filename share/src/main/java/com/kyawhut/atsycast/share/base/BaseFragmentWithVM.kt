package com.kyawhut.atsycast.share.base

import androidx.viewbinding.ViewBinding

/**
 * @author kyawhtut
 * @date 9/3/21
 */
abstract class BaseFragmentWithVM<VM : BaseViewModel, VB : ViewBinding> : BaseFragment<VB>() {

    abstract val vm: VM
}