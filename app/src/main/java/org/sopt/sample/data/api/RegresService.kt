package org.sopt.sample.data.api

import org.sopt.sample.data.model.ProfileListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RegresService {
    /**
     * 프로필 조회 API
     */
    @GET("api/users")
    suspend fun getProfileList(
        @Query("page") page: Int = 2
    ): ProfileListResponse
}