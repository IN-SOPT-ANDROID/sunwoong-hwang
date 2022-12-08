package org.sopt.sample.presentation.signin.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.domain.repository.AuthRepository
import org.sopt.sample.util.Event
import org.sopt.sample.util.extension.addSourceList

class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val emailRegex = Regex(EMAIL_REGEX)
    private val passwordRegex = Regex(PASSWORD_REGEX)

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

    fun signIn() {
        viewModelScope.launch {
            runCatching {
                authRepository.signIn(SignInRequest(email.value!!, password.value!!))
            }.fold({
                _signInEvent.value = Event(true)
            }, {
                _signInEvent.value = Event(false)
            })
        }
    }

    companion object {
        private const val EMAIL_REGEX =
            "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,10}+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\$"
        private const val PASSWORD_REGEX =
            "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{6,12}\$"
    }
}