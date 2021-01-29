package com.appetiser.exam1.data


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class Track(
    @PrimaryKey @ColumnInfo(name = "id") val trackId: String,
    val trackName: String,
    val artworkUrl30: String,
    val artworkUrl60: String,
    val artworkUrl100: String,
    val primaryGenreName: String,
    val currency: String,
    val trackPrice: Double,
    val previewUrl: String,
    val longDescription: String
) {
    fun formattedTrackPrice() =
        currency + " " + String.format("%.2f", trackPrice)

    fun isPreviewAvailable() : Boolean =
        previewUrl != null && previewUrl.isNotEmpty()
}
