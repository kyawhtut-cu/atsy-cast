package com.kyawhut.atsycast.share.base

import android.content.Context
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.PresenterSelector

abstract class BaseCardPresenterSelector<T : BaseCardType>(
    private val context: Context
) : PresenterSelector() {

    private val presenters: MutableMap<T, Presenter> = mutableMapOf()

    abstract fun getCardType(item: Any?): T

    abstract fun getPresenter(type: T, context: Context): BaseCardPresenter<*>

    override fun getPresenter(item: Any?): Presenter {
        val cardType: T = getCardType(item)
        var presenter = presenters[cardType]
        if (presenter != null) return presenter
        presenter = getPresenter(cardType, context)
        presenters[cardType] = presenter
        return presenter
    }
}
