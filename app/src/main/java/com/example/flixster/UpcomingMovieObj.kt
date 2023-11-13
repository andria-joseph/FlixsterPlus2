package com.example.flixster

import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Keep
@Serializable
class UpcomingMovieObj {
    @JvmField
    @SerializedName("title")
    var title: String? = null

    @JvmField
    @SerializedName("overview")
    var overview: String? = null

    @JvmField
    @SerializedName("poster_path")
    var posterImageUrl: String? = null

    @JvmField
    @SerializedName("original_language")
    var originalLanguage: String? = null

    @JvmField
    @SerializedName("release_date")
    var releaseDate: String? = null


}