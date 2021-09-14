package com.kyawhut.atsycast.share.ui.card

import android.content.Context
import com.google.gson.Gson
import com.kyawhut.atsycast.share.base.BaseCardPresenter
import com.kyawhut.atsycast.share.base.BaseCardPresenterSelector
import com.kyawhut.atsycast.share.model.ActionModel

/**
 * @author kyawhtut
 * @date 9/7/21
 */
class DetailCard(context: Context) : BaseCardPresenterSelector<DetailCardType>(context) {

    override fun getCardType(item: Any?): DetailCardType {
        return when (item) {
            is ActionModel -> DetailCardType.ACTION
            else -> throw RuntimeException("Unsupported item => ${Gson().toJson(item)}")
        }
    }

    override fun getPresenter(type: DetailCardType, context: Context): BaseCardPresenter<*> {
        return when (type) {
            DetailCardType.ACTION -> ActionCardPresenter(context)
        }
    }
}