package com.kyawhut.atsycast.share.model

import androidx.leanback.widget.DiffCallback
import androidx.recyclerview.widget.DiffUtil

/**
 * @author kyawhtut
 * @date 1/21/21
 */
data class ActionModel private constructor(
    val id: Long,
    val actionName: String,
    val icon: Int
) {

    companion object {
        val diff = object : DiffUtil.ItemCallback<ActionModel>() {
            override fun areItemsTheSame(oldItem: ActionModel, newItem: ActionModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ActionModel, newItem: ActionModel): Boolean {
                return oldItem == newItem
            }
        }

        val diffTv = object : DiffCallback<ActionModel>() {
            override fun areItemsTheSame(oldItem: ActionModel, newItem: ActionModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ActionModel, newItem: ActionModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    class Builder {
        var id: Long = 0L
        var actionName: String = ""
        var icon: Int = 0

        fun build() = ActionModel(id, actionName, icon)
    }
}

fun action(block: ActionModel.Builder.() -> Unit) =
    ActionModel.Builder().also(block).build()
