package org.sopt.sample.data.source

import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.data.model.SignInResponse
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.data.model.SignUpResponse

interface AuthDataSource {
    suspend fun signIn(signInRequest: SignInRequest): SignInResponse
    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse
}