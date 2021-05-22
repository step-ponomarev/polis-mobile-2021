package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserED
import ru.mail.polis.dao.users.UserService

class FirstCreationViewModel : ViewModel() {

    private val userService: IUserService = UserService()

    fun addUser(userED: UserED) {
        viewModelScope.launch(Dispatchers.IO) {
            userService.addUser(userED)
        }
    }
}