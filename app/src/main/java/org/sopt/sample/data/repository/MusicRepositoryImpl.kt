package org.sopt.sample.data.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.sample.data.model.MusicListResponse
import org.sopt.sample.data.model.MusicResponse
import org.sopt.sample.data.source.remote.MusicDataSource
import org.sopt.sample.domain.repository.MusicRepository
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val musicDataSource: MusicDataSource
) : MusicRepository {
    override suspend fun registerMusic(
        image: MultipartBody.Part,
        contents: RequestBody
    ): MusicResponse = musicDataSource.registerMusic(image, contents)

    override suspend fun getMusicList(): MusicListResponse = musicDataSource.getMusicList()
}