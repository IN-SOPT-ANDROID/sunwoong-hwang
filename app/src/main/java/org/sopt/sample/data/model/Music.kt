package org.sopt.sample.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable
data class MusicRequest(
    val title: String,
    val singer: String
) {
    fun toJsonObject() = buildJsonObject {
        put("title", title)
        put("singer", singer)
    }.toString().toRequestBody("application/json".toMediaType())
}

@Serializable
data class MusicResponse(
    val statusCode: Int,
    val success: Boolean,
    val message: String,
    @SerialName("data") val result: Music
)

@Serializable
data class MusicListResponse(
    val statusCode: Int,
    val success: Boolean,
    val message: String,
    @SerialName("data") val result: List<Music>
)

@Serializable
data class Music(
    val id: Int,
    val image: String,
    val title: String,
    val singer: String
)