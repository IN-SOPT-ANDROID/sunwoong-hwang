package org.sopt.sample.data.model

import java.io.Serializable

data class UserDetail(
    val profileUrl: String = "https://user-images.githubusercontent.com/81796317/193957207-929b445f-68f7-4ee8-8d2a-2fbd15660dde.jpeg",
    val nickname: String,
    val part: String,
    val mbti: String
): Serializable
