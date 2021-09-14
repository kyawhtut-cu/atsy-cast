package com.kyawhut.atsycast.share.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kyawhut.atsycast.share.utils.SourceType
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author kyawhtut
 * @date 9/8/21
 */
internal object DBConverter {

    private const val DATE_FORMAT = "yyyy-MM-dd hh:mm a"

    @TypeConverter
    @JvmStatic
    fun Date.toStringValue(): String {
        return SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(this)
    }

    @TypeConverter
    @JvmStatic
    fun String.toDate(): Date {
        return SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).parse(this) ?: Date()
    }

    @TypeConverter
    @JvmStatic
    fun String.toSourceType(): SourceType {
        return SourceType.getSourceType(this)
    }

    @TypeConverter
    @JvmStatic
    fun SourceType.toStringValue(): String {
        return this.type
    }

    @TypeConverter
    @JvmStatic
    fun List<Pair<String, String>>.toStringValue(): String {
        return Gson().toJson(this)
    }

    @TypeConverter
    @JvmStatic
    fun String.toCustomHeader(): List<Pair<String, String>> {
        return Gson().fromJson(this, object : TypeToken<List<Pair<String, String>>>() {}.type)
    }
}
