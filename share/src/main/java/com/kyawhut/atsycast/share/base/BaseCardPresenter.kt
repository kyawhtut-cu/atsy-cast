package com.kyawhut.atsycast.share.base

import android.content.Context
import android.view.ViewGroup
import androidx.leanback.widget.Presenter

abstract class BaseCardPresenter<T : BaseCardView<*>>(val context: Context) : Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        return ViewHolder(onCreateView())
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, item: Any?) {
        if (item == null) return
        (viewHolder?.view as T).bindUI(item)
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
    }

    //  invoked when a new view is created, return the newly created view
    abstract fun onCreateView(): T

    //  to update card's view with the data bound to it
}
