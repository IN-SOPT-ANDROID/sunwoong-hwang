package org.sopt.sample.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)

@Serializable
data class SignInResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("result") val result: AuthResult
)

@Serializable
data class SignUpRequest(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("name") val name: String
)

@Serializable
data class SignUpResponse(
    @SerialName("status") val status: Int,
    @SerialName("message") val message: String,
    @SerialName("newUser") val result: AuthResult
)

@Serializable
data class AuthResult(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("profileImage") val profileImage: String?,
    @SerialName("bio") val bio: String?,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)