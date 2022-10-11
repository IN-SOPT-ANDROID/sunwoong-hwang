package org.sopt.sample.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.model.User
import org.sopt.sample.data.model.UserInformation
import org.sopt.sample.data.repository.HomeRepository
import org.sopt.sample.presentation.common.Event

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _userInformations = MutableLiveData<List<UserInformation>>()
    val userInformations: LiveData<List<UserInformation>>
        get() = _userInformations

    private var user: User? = null

    private val _signUpEvent = MutableLiveData<Event<Boolean>>()
    val signUpEvent: LiveData<Event<Boolean>>
        get() = _signUpEvent

    fun setUser(user: User) {
        this.user = user
        _signUpEvent.value = Event(true)
    }

    fun getGithubInformations() {
        val githubInformations = homeRepository.getGithubInformations()
        val userInformations = mutableListOf<UserInformation>()
        userInformations.add(user!!)
        if (githubInformations != null) {
            userInformations.addAll(githubInformations)
        }
        _userInformations.value = userInformations
    }
}