package org.sopt.sample.data.source

import org.sopt.sample.data.api.AuthService
import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.data.model.SignInResponse
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.data.model.SignUpResponse

class AuthDataSourceImpl(private val authService: AuthService) : AuthDataSource {
    override suspend fun signIn(signInRequest: SignInRequest): SignInResponse {
        return authService.signIn(signInRequest)
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        return authService.signUp(signUpRequest)
    }
}