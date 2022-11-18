package org.sopt.sample.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.sopt.sample.data.api.ApiClient
import org.sopt.sample.data.api.AuthService
import org.sopt.sample.data.api.RegresService
import org.sopt.sample.data.repository.AuthRepository
import org.sopt.sample.data.repository.RegresRepository
import org.sopt.sample.data.source.AuthDataSourceImpl
import org.sopt.sample.data.source.RegresDataSourceImpl
import org.sopt.sample.presentation.home.viewmodel.HomeViewModel
import org.sopt.sample.presentation.signin.viewmodel.SignInViewModel
import org.sopt.sample.presentation.signup.viewmodel.SignUpViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> SignInViewModel(
                AuthRepository(
                    AuthDataSourceImpl(
                        ApiClient.getRetrofitForSopt().create(AuthService::class.java)
                    )
                )
            ) as T
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel(
                AuthRepository(
                    AuthDataSourceImpl(
                        ApiClient.getRetrofitForSopt().create(AuthService::class.java)
                    )
                )
            ) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(
                RegresRepository(
                    RegresDataSourceImpl(
                        ApiClient.getRetrofitForRegres().create(RegresService::class.java)
                    )
                )
            ) as T
            else -> throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
        }
    }
}