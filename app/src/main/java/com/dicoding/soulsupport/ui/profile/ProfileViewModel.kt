package com.dicoding.soulsupport.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.soulsupport.data.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _userName = MutableLiveData<String?>()
    val userName: LiveData<String?> get() = _userName

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> get() = _userEmail

    private suspend fun fetchUserInfo() {
        repository.getUserInfo().collect { authModel ->
            _userName.postValue(authModel.name)
            _userEmail.postValue(authModel.email)
        }
    }

    init {
        viewModelScope.launch {
            fetchUserInfo()
        }
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
