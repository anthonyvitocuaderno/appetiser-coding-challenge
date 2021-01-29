package com.appetiser.exam1.data

import com.google.gson.annotations.SerializedName

data class TrackSearchResponse(
    @field:SerializedName("results") val results: List<Track>,
    @field:SerializedName("resultCount") val resultCount: Int
)