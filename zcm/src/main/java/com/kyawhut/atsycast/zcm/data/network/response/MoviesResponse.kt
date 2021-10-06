package com.kyawhut.atsycast.zcm.data.network.response

import androidx.annotation.Keep
import androidx.leanback.widget.DiffCallback
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @author kyawhtut
 * @date 9/10/21
 */
@Keep
data class MoviesResponse(
    @SerializedName("id")
    val moviesID: Int,
    @SerializedName("type")
    val moviesType: String,
    @SerializedName("title")
    val moviesTitle: String,
    @SerializedName("description")
    val moviesDescription: String,
    @SerializedName("year")
    val moviesYear: String,
    @SerializedName("imdb")
    val imdb: String?,
    @SerializedName("image")
    val moviesImage: String,
    @SerializedName("cover")
    val moviesCover: String?,
    @SerializedName("genres")
    val moviesGenres: List<GenresResponse>,
    @SerializedName("sources")
    val moviesSource: List<MoviesSourceResponse>,
) : Serializable {
    companion object {
        val diff = object : DiffCallback<MoviesResponse>() {
            override fun areItemsTheSame(
                oldItem: MoviesResponse,
                newItem: MoviesResponse
            ): Boolean {
                return oldItem.moviesID == newItem.moviesID
            }

            override fun areContentsTheSame(
                oldItem: MoviesResponse,
                newItem: MoviesResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    val isMovies: Boolean
        get() = moviesType == "movie"
}
