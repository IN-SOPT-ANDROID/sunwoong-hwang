package org.sopt.sample.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.sopt.sample.data.model.Profile
import org.sopt.sample.data.repository.RegresRepository
import org.sopt.sample.util.Event

class HomeViewModel(private val regresRepository: RegresRepository) : ViewModel() {
    private val _profileList = MutableLiveData<List<Profile>>()
    val profileList: LiveData<List<Profile>>
        get() = _profileList

    private val _profileListEvent = MutableLiveData<Event<Boolean>>()
    val profileListEvent: LiveData<Event<Boolean>>
        get() = _profileListEvent

    init {
        getProfileList()
    }

    fun getProfileList(page: Int = 2) {
        viewModelScope.launch {
            kotlin.runCatching {
                regresRepository.getProfileList(page)
            }.onSuccess {
                _profileList.value = it.profileList
            }.onFailure {
                _profileListEvent.value = Event(false)
            }
        }
    }
}