package org.sopt.sample.data.model

import java.io.Serializable

data class User(
    val id: String,
    val password: String,
    val mbti: String,
    val part: String,
    val nickname: String
): Serializable
