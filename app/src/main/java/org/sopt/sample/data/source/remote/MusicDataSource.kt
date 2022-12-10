package org.sopt.sample.data.source.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.sample.data.api.MusicService

class MusicDataSource(private val musicService: MusicService) {
    suspend fun registerMusic(image: MultipartBody.Part, contents: RequestBody) =
        musicService.registerMusic(image, contents)

    suspend fun getMusicList() = musicService.getMusicList()
}