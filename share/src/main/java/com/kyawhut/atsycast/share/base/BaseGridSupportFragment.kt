package com.kyawhut.atsycast.share.base

import android.os.Bundle
import android.view.View
import androidx.leanback.app.BrowseSupportFragment

abstract class BaseGridSupportFragment<VM : BaseViewModel> : BaseGridFragment<VM>(),
    BrowseSupportFragment.MainFragmentAdapterProvider {

    private val fragmentAdapter by lazy { BrowseSupportFragment.MainFragmentAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentAdapter.fragmentHost.notifyViewCreated(mainFragmentAdapter)
    }

    override fun getMainFragmentAdapter(): BrowseSupportFragment.MainFragmentAdapter<*> {
        return fragmentAdapter
    }
}
