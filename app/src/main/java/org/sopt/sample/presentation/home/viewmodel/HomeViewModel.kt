package org.sopt.sample.presentation.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.sample.data.model.UserDetail

class HomeViewModel : ViewModel() {

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    fun setUserDetail(userDetail: UserDetail) {
        _userDetail.value = userDetail
    }
}