package org.sopt.sample.data.source.remote

import org.sopt.sample.data.api.RegresService
import org.sopt.sample.data.model.ProfileListResponse

class RegresDataSource(private val regresService: RegresService) {
    suspend fun getProfileList(): ProfileListResponse =
        regresService.getProfileList()
}