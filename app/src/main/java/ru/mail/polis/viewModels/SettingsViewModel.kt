package ru.mail.polis.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

class SettingsViewModel : ViewModel() {

    private val userService: IUserService = UserService()

    var userED = MutableLiveData<UserED>()

    fun getUserInfo(email: String) {

        viewModelScope.launch(Dispatchers.IO) {

            val user = userService.findUserByEmail(email)

            withContext(Dispatchers.Main) {
                userED.value = user!!
            }
        }
    }

    fun updateUser(userED: UserED) {
        viewModelScope.launch(Dispatchers.IO) {
            userService.updateUserByEmail(userED.email!!, userED)
        }
    }

    fun getUser(): LiveData<UserED> = userED
}