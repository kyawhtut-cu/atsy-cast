package com.kyawhut.atsycast.share.utils.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**
 * @author kyawhtut
 * @date 9/7/21
 */
object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("rvAdapter", "rvLayoutManager", requireAll = true)
    fun RecyclerView.setAdapter(
        adapter: RecyclerView.Adapter<*>,
        layoutManager: RecyclerView.LayoutManager
    ) {
        this.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
        }
    }
}
