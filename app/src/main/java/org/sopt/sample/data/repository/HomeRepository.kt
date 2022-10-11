package org.sopt.sample.data.repository

import org.sopt.sample.data.model.GithubInformation
import org.sopt.sample.data.source.HomeDataSourceImpl

class HomeRepository(private val homeDataSourceImpl: HomeDataSourceImpl) {

    fun getGithubInformation(): List<GithubInformation>? {
        return homeDataSourceImpl.getGithubInformation()
    }
}