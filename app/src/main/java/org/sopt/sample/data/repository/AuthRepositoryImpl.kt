package org.sopt.sample.data.repository

import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.data.model.SignInResponse
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.data.model.SignUpResponse
import org.sopt.sample.data.source.remote.AuthDataSource
import org.sopt.sample.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authDataSource: AuthDataSource) : AuthRepository {
    override suspend fun signIn(signInRequest: SignInRequest): SignInResponse =
        authDataSource.signIn(signInRequest)

    override suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse =
        authDataSource.signUp(signUpRequest)
}