package org.sopt.sample.data.source

import org.sopt.sample.data.api.RegresService
import org.sopt.sample.data.model.ProfileListResponse

class RegresDataSourceImpl(private val regresService: RegresService) : RegresDataSource {
    override suspend fun getProfileList(page: Int): ProfileListResponse =
        regresService.getProfileList(page)
}