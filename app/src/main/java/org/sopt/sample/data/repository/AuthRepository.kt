package org.sopt.sample.data.repository

import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.data.model.SignInResponse
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.data.model.SignUpResponse
import org.sopt.sample.data.source.AuthDataSource

class AuthRepository(private val authDataSource: AuthDataSource) {
    suspend fun signIn(signInRequest: SignInRequest): SignInResponse =
        authDataSource.signIn(signInRequest)

    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse =
        authDataSource.signUp(signUpRequest)
}