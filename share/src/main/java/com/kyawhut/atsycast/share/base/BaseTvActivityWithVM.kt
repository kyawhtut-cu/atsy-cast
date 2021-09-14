package com.kyawhut.atsycast.share.base

import androidx.viewbinding.ViewBinding

/**
 * @author kyawhtut
 * @date 8/31/21
 */
abstract class BaseTvActivityWithVM<VB : ViewBinding, VM : BaseViewModel> : BaseTvActivity<VB>() {

    protected abstract val vm: VM
}
