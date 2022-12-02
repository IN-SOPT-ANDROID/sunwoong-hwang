package org.sopt.sample.domain.repository

import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.data.model.SignInResponse
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.data.model.SignUpResponse

interface AuthRepository {
    suspend fun signIn(signInRequest: SignInRequest): SignInResponse
    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse
}