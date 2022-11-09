package org.sopt.sample.data.api

import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.data.model.SignInResponse
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.data.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    /**
     * 로그인 API
     */
    @POST("api/user/signin")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): SignInResponse

    /**
     * 회원가입 API
     */
    @POST("api/user/signup")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): SignUpResponse
}