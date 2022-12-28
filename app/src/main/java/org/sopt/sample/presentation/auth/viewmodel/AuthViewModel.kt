package org.sopt.sample.presentation.auth.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.sopt.sample.data.model.SignInRequest
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.domain.repository.AuthRepository
import org.sopt.sample.util.Event
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
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
    val name = MutableLiveData<String>()
    val isValidName: LiveData<Boolean> = Transformations.map(name) {
        !it.isNullOrEmpty()
    }
    private val _authEvent = MutableLiveData<Event<Boolean>>()
    val authEvent: LiveData<Event<Boolean>>
        get() = _authEvent

    private fun checkEmailPattern(pattern: String = ""): Boolean = emailRegex.matches(pattern)

    private fun checkPasswordPattern(pattern: String = ""): Boolean = passwordRegex.matches(pattern)

    fun signIn() {
        viewModelScope.launch {
            runCatching {
                authRepository.signIn(SignInRequest(email.value!!, password.value!!))
            }.fold({
                _authEvent.value = Event(true)
            }, {
                _authEvent.value = Event(false)
            })
        }
    }

    fun signUp() {
        viewModelScope.launch {
            runCatching {
                authRepository.signUp(SignUpRequest(email.value!!, password.value!!, name.value!!))
            }.fold({
                _authEvent.value = Event(true)
            }, {
                _authEvent.value = Event(false)
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