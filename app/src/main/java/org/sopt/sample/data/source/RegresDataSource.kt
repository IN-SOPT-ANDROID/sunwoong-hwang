package org.sopt.sample.data.source

import org.sopt.sample.data.model.ProfileListResponse

interface RegresDataSource {
    suspend fun getProfileList(page: Int): ProfileListResponse
}