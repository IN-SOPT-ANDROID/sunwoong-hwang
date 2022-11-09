package org.sopt.sample.presentation.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.data.repository.AuthRepository
import org.sopt.sample.util.EMAIL
import org.sopt.sample.util.Event
import org.sopt.sample.util.NAME
import org.sopt.sample.util.PASSWORD

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val user: MutableMap<String, Boolean> = mutableMapOf(
        EMAIL to false,
        PASSWORD to false,
        NAME to false
    )

    private val valid = setOf(
        EMAIL, PASSWORD, NAME
    )

    private val _signUpEvent = MutableLiveData<Event<Boolean>>()
    val signUpEvent: LiveData<Event<Boolean>>
        get() = _signUpEvent

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

    fun signUp(signUpRequest: SignUpRequest) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signUp(signUpRequest)
            }.onSuccess {
                if (it.status == 201) {
                    _signUpEvent.value = Event(true)
                } else {
                    _signUpEvent.value = Event(false)
                }
            }.onFailure {
                _signUpEvent.value = Event(false)
            }
        }
    }
}