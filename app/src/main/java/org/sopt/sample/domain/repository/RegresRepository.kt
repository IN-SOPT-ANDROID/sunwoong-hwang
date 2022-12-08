package org.sopt.sample.domain.repository

import org.sopt.sample.data.model.ProfileListResponse

interface RegresRepository {
    suspend fun getProfileList(): ProfileListResponse
}