package org.sopt.sample.presentation.signup.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.domain.repository.AuthRepository
import org.sopt.sample.util.Event
import org.sopt.sample.util.extension.addSourceList

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {
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
    private val isValidName: LiveData<Boolean> = Transformations.map(name) {
        !it.isNullOrEmpty()
    }

    val isValid = MediatorLiveData<Boolean>().apply {
        addSourceList(isValidEmail, isValidPassword, isValidName) {
            checkSignUp()
        }
    }

    private val _signUpEvent = MutableLiveData<Event<Boolean>>()
    val signUpEvent: LiveData<Event<Boolean>>
        get() = _signUpEvent

    private fun checkEmailPattern(pattern: String = ""): Boolean = emailRegex.matches(pattern)

    private fun checkPasswordPattern(pattern: String = ""): Boolean = passwordRegex.matches(pattern)

    private fun checkSignUp(): Boolean {
        if (isValidEmail.value == null || isValidPassword.value == null || isValidName.value == null) return false
        return isValidEmail.value!! && isValidPassword.value!! && isValidName.value!!
    }

    fun signUp() {
        viewModelScope.launch {
            runCatching {
                authRepository.signUp(SignUpRequest(email.value!!, password.value!!, name.value!!))
            }.fold({
                _signUpEvent.value = Event(true)
            }, {
                _signUpEvent.value = Event(false)
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