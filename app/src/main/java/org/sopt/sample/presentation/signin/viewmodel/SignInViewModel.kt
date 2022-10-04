package org.sopt.sample.presentation.signin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.model.User
import org.sopt.sample.presentation.common.Event

class SignInViewModel : ViewModel() {

    private var user: User? = null

    private val _signInEvent = MutableLiveData<Event<Boolean>>()
    val signInEvent: LiveData<Event<Boolean>> = _signInEvent

    fun setUser(user: User) {
        this.user = user
    }

    fun signIn(id: String, password: String) {
        _signInEvent.value = Event(user != null && id == user!!.id && password == user!!.password)
    }
}