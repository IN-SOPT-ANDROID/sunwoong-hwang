package org.sopt.sample.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val email: String,
    val password: String
)

@Serializable
data class SignInResponse(
    val status: Int,
    val message: String,
    val result: AuthResult
)

@Serializable
data class SignUpRequest(
    val email: String,
    val password: String,
    val name: String
)

@Serializable
data class SignUpResponse(
    val status: Int,
    val message: String,
    @SerialName("newUser") val result: AuthResult
)

@Serializable
data class AuthResult(
    val id: Int,
    val name: String,
    val profileImage: String?,
    val bio: String?,
    val email: String,
    val password: String
)