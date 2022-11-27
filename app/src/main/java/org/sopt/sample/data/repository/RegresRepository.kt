package org.sopt.sample.data.repository

import org.sopt.sample.data.model.ProfileListResponse
import org.sopt.sample.data.source.RegresDataSource

class RegresRepository(private val regresDataSource: RegresDataSource) {
    suspend fun getProfileList(): ProfileListResponse =
        regresDataSource.getProfileList()
}