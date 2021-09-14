package com.kyawhut.atsycast.share.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
