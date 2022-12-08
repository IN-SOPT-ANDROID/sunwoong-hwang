package org.sopt.sample.data.source.remote

import org.sopt.sample.data.api.AuthService
import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.data.model.SignInResponse
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.data.model.SignUpResponse

class AuthDataSource(private val authService: AuthService) {
    suspend fun signIn(signInRequest: SignInRequest): SignInResponse =
        authService.signIn(signInRequest)

    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse =
        authService.signUp(signUpRequest)
}