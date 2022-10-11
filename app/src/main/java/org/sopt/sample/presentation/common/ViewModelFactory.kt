package org.sopt.sample.presentation.common

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.sopt.sample.data.repository.HomeRepository
import org.sopt.sample.data.source.HomeDataSourceImpl
import org.sopt.sample.presentation.home.viewmodel.HomeViewModel
import org.sopt.sample.presentation.signin.viewmodel.SignInViewModel
import org.sopt.sample.presentation.signup.viewmodel.SignUpViewModel
import org.sopt.sample.util.AssetsLoader

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel() as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel() as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(HomeRepository(HomeDataSourceImpl(AssetsLoader(context)))) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}