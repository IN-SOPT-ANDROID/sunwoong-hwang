package org.sopt.sample.data.repository

import org.sopt.sample.data.model.ProfileListResponse
import org.sopt.sample.data.source.remote.RegresDataSource
import org.sopt.sample.domain.repository.RegresRepository
import javax.inject.Inject

class RegresRepositoryImpl @Inject constructor(
    private val regresDataSource: RegresDataSource
) : RegresRepository {
    override suspend fun getProfileList(): ProfileListResponse =
        regresDataSource.getProfileList()
}