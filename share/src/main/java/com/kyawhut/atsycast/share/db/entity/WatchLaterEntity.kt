package com.kyawhut.atsycast.share.db.entity

import androidx.leanback.widget.DiffCallback
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.kyawhut.atsycast.share.utils.SourceType
import java.util.*

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@Entity(tableName = "table_watch_later")
data class WatchLaterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "video_data")
    val videoData: String,
    @ColumnInfo(name = "video_source_type")
    val videoSourceType: SourceType,
    @ColumnInfo(name = "created_at")
    val createdAt: Date
) {

    inline fun <reified C : Any> getData(): C {
        return Gson().fromJson(videoData, C::class.java)
    }

    companion object {
        val diff = object : DiffCallback<WatchLaterEntity>() {
            override fun areItemsTheSame(
                oldItem: WatchLaterEntity,
                newItem: WatchLaterEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: WatchLaterEntity,
                newItem: WatchLaterEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class Builder {
        var id: Int = 0
        var videoData: String = ""
        var videoSourceType: SourceType = SourceType.UNKNOWN
        var createdAt: Date = Date()

        fun build(): WatchLaterEntity = WatchLaterEntity(id, videoData, videoSourceType, createdAt)
    }
}

fun watchLater(block: WatchLaterEntity.Builder.() -> Unit): WatchLaterEntity {
    return WatchLaterEntity.Builder().also(block).build()
}
