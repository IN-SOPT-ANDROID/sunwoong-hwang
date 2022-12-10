package org.sopt.sample.domain.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.sample.data.model.MusicResponse

interface MusicRepository {
    suspend fun registerMusic(image: MultipartBody.Part, contents: RequestBody): MusicResponse
}