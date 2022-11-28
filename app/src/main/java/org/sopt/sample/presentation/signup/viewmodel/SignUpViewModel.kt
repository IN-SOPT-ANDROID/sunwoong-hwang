package org.sopt.sample.presentation.signup.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.sopt.sample.data.model.SignUpRequest
import org.sopt.sample.data.repository.AuthRepository
import org.sopt.sample.util.Event
import org.sopt.sample.util.extension.addSourceList

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {
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

    fun signUp(signUpRequest: SignUpRequest) {
        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signUp(signUpRequest)
            }.onSuccess {
                _signUpEvent.value = Event(true)
            }.onFailure {
                _signUpEvent.value = Event(false)
            }
        }
    }
}