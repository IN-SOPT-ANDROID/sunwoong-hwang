package org.sopt.sample.data.source

import com.google.gson.Gson
import org.sopt.sample.data.model.GithubInformation
import org.sopt.sample.util.AssetsLoader

class HomeDataSourceImpl(private val assetsLoader: AssetsLoader) : HomeDataSource {

    private val gson = Gson()

    override fun getGithubInformations(): List<GithubInformation>? {
        return assetsLoader.getJsonString(DATA)?.let { stringData ->
            gson.fromJson(stringData, Array<GithubInformation>::class.java).toList()
        }
    }

    companion object {
        private const val DATA = "fake_repo_list"
    }
}