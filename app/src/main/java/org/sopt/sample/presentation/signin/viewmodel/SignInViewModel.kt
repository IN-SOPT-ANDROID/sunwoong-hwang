package org.sopt.sample.presentation.signin.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.data.repository.AuthRepository
import org.sopt.sample.util.Event
import org.sopt.sample.util.extension.addSourceList

class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val emailRegex =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$")
    private val passwordRegex =
        Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{6,12}\$")

    val email = MutableLiveData<String>()
    val isValidEmail: LiveData<Boolean> = Transformations.map(email) {
        checkEmailPattern(it)
    }

    val password = MutableLiveData<String>()
    val isValidPassword: LiveData<Boolean> = Transformations.map(password) {
        checkPasswordPattern(it)
    }

    val isValid = MediatorLiveData<Boolean>().apply {
        addSourceList(isValidEmail, isValidPassword) {
            checkSignIn()
        }
    }

    private val _signInEvent = MutableLiveData<Event<Boolean>>()
    val signInEvent: LiveData<Event<Boolean>>
        get() = _signInEvent

    private fun checkEmailPattern(pattern: String = ""): Boolean = emailRegex.matches(pattern)

    private fun checkPasswordPattern(pattern: String = ""): Boolean = passwordRegex.matches(pattern)

    private fun checkSignIn(): Boolean {
        if (isValidEmail.value == null || isValidPassword.value == null) return false
        return isValidEmail.value!! && isValidPassword.value!!
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