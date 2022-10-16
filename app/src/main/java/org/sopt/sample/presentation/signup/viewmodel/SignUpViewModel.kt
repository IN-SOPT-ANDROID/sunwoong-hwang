package org.sopt.sample.presentation.signup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.model.User
import org.sopt.sample.presentation.common.Event

class SignUpViewModel : ViewModel() {

    private var user: User? = null

    private val _signUpEvent = MutableLiveData<Event<Boolean>>()
    val signUpEvent: LiveData<Event<Boolean>>
        get() = _signUpEvent

    fun getUser(): User? {
        return user
    }

    fun signUp(user: User) {
        if (user.id.length in 6..10 && user.password.length in 8..12) {
            this.user = user
            _signUpEvent.value = Event(true)
        } else {
            _signUpEvent.value = Event(false)
        }
    }
}