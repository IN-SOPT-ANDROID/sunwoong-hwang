package org.sopt.sample.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.sopt.sample.data.api.ApiClient
import org.sopt.sample.data.api.AuthService
import org.sopt.sample.data.api.MusicService
import org.sopt.sample.data.api.RegresService
import org.sopt.sample.data.repository.AuthRepositoryImpl
import org.sopt.sample.data.repository.MusicRepositoryImpl
import org.sopt.sample.data.repository.RegresRepositoryImpl
import org.sopt.sample.data.source.remote.AuthDataSource
import org.sopt.sample.data.source.remote.MusicDataSource
import org.sopt.sample.data.source.remote.RegresDataSource
import org.sopt.sample.presentation.home.viewmodel.HomeViewModel
import org.sopt.sample.presentation.music.viewmodel.MusicViewModel
import org.sopt.sample.presentation.signin.viewmodel.SignInViewModel
import org.sopt.sample.presentation.signup.viewmodel.SignUpViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> SignInViewModel(
                AuthRepositoryImpl(
                    AuthDataSource(
                        ApiClient.soptRetrofit.create(AuthService::class.java)
                    )
                )
            ) as T
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel(
                AuthRepositoryImpl(
                    AuthDataSource(
                        ApiClient.soptRetrofit.create(AuthService::class.java)
                    )
                )
            ) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                RegresRepositoryImpl(
                    RegresDataSource(
                        ApiClient.regresRetrofit.create(RegresService::class.java)
                    )
                )
            ) as T
            modelClass.isAssignableFrom(MusicViewModel::class.java) -> MusicViewModel(
                MusicRepositoryImpl(
                    MusicDataSource(
                        ApiClient.musicRetrofit.create(MusicService::class.java)
                    )
                )
            ) as T
            else -> throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
        }
    }
}