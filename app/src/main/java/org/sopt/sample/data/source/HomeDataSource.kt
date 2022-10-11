package org.sopt.sample.data.source

import org.sopt.sample.data.model.GithubInformation

interface HomeDataSource {

    fun getGithubInformation(): List<GithubInformation>?
}