package com.kyawhut.atsycast.share.db.entity

import androidx.leanback.widget.DiffCallback
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kyawhut.atsycast.share.model.SubTitleModel
import com.kyawhut.atsycast.share.utils.SourceType
import java.util.*

/**
 * @author kyawhtut
 * @date 9/8/21
 */
@Entity(tableName = "table_recently_watch")
data class RecentlyWatchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "video_id")
    val videoID: String,
    @ColumnInfo(name = "video_title")
    val videoTitle: String,
    @ColumnInfo(name = "video_cover")
    val videoCover: String,
    @ColumnInfo(name = "video_url")
    val videoURL: String,
    @ColumnInfo(name = "video_duration")
    val videoDuration: Long,
    @ColumnInfo(name = "video_last_position")
    val videoLastPosition: Long,
    @ColumnInfo(name = "video_agent")
    val videoAgent: String?,
    @ColumnInfo(name = "video_cookies")
    val videoCookies: String?,
    @ColumnInfo(name = "video_custom_header")
    val videoCustomHeader: List<Pair<String, String>> = mutableListOf(),
    @ColumnInfo(name = "video_related_video")
    val videoRelatedVideo: String = "",
    @ColumnInfo(name = "video_source_type")
    val videoSourceType: SourceType,
    @ColumnInfo(name = "is_adult")
    val isAdult: Boolean,
    @ColumnInfo(name = "video_subtitle")
    val videoSubtitle: List<SubTitleModel>,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    @ColumnInfo(name = "updated_at")
    val updateAt: Date
) {

    companion object {
        val diff = object : DiffCallback<RecentlyWatchEntity>() {

            override fun areItemsTheSame(
                oldItem: RecentlyWatchEntity,
                newItem: RecentlyWatchEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: RecentlyWatchEntity,
                newItem: RecentlyWatchEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun getWidth(width: Int): Int {
        return (width * videoLastPosition.toInt()) / videoDuration.toInt()
    }

    class Builder {
        var id: Int = 0
        var videoID: String = ""
        var videoTitle: String = ""
        var videoCover: String = ""
        var videoURL: String = ""
        var videoDuration: Long = 0L
        var videoLastPosition: Long = 0L
        var videoAgent: String? = null
        var videoCookies: String? = null
        var videoCustomHeader: List<Pair<String, String>> = mutableListOf()
        var videoRelatedVideo: String = ""
        var videoSourceType: SourceType = SourceType.UNKNOWN
        var updateAt: Date = Date()
        var isAdult: Boolean = false
        var videoSubtitle: List<SubTitleModel> = mutableListOf()

        fun build(): RecentlyWatchEntity = RecentlyWatchEntity(
            id,
            videoID,
            videoTitle,
            videoCover,
            videoURL,
            videoDuration,
            videoLastPosition,
            videoAgent,
            videoCookies,
            videoCustomHeader,
            videoRelatedVideo,
            videoSourceType,
            isAdult,
            videoSubtitle,
            Date(),
            updateAt
        )
    }
}

fun recentlyWatch(block: RecentlyWatchEntity.Builder.() -> Unit): RecentlyWatchEntity {
    return RecentlyWatchEntity.Builder().also(block).build()
}
