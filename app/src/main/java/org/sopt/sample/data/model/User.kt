package org.sopt.sample.data.model

import java.io.Serializable

data class User(
    val id: String,
    val password: String,
    val mbti: String,
    val part: String,
    val nickname: String,
    val profileUrl: String = "https://user-images.githubusercontent.com/81796317/193957207-929b445f-68f7-4ee8-8d2a-2fbd15660dde.jpeg",
): Serializable
