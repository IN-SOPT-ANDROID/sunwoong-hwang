package org.sopt.sample.presentation.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.data.repository.AuthRepository
import org.sopt.sample.util.EMAIL
import org.sopt.sample.util.Event
import org.sopt.sample.util.PASSWORD

class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val user: MutableMap<String, Boolean> = mutableMapOf(
        EMAIL to false,
        PASSWORD to false
    )

    private val valid = setOf(
        EMAIL, PASSWORD
    )

    private val _signInEvent = MutableLiveData<Event<Boolean>>()
    val signInEvent: LiveData<Event<Boolean>>
        get() = _signInEvent

    private val _isPromising = MutableLiveData<Event<Boolean>>()
    val isPromising: LiveData<Event<Boolean>>
        get() = _isPromising

    fun setUserStatus(key: String, promising: Boolean) {
        if (key in valid) {
            user[key] = promising
            checkUserStatus()
        }
    }

    private fun checkUserStatus() {
        _isPromising.value = Event(user.all { it.value })
    }

    fun signIn(signInRequest: SignInRequest) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signIn(signInRequest)
            }.onSuccess {
                _signInEvent.value = Event(true)
            }.onFailure {
                _signInEvent.value = Event(false)
            }
        }
    }
}