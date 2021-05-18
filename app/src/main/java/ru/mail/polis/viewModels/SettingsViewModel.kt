package ru.mail.polis.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mail.polis.dao.users.IUserService
import ru.mail.polis.dao.users.UserService

class SettingsViewModel : ViewModel() {

    private val userService: IUserService = UserService()
    private val email = FirebaseAuth.getInstance().currentUser.email

    val name = MutableLiveData<String>()
    val surname = MutableLiveData<String>()
    val age = MutableLiveData<Long>()
    val phone = MutableLiveData<String>()
    val externalAccounts = MutableLiveData<List<String>>()
    val photo = MutableLiveData<String>()

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userService.findUserByEmail(email)

            name.value = user?.name
            surname.value = user?.surname
            age.value = user?.age
            phone.value = user?.phone
            externalAccounts.value = user?.externalAccounts ?: emptyList()
            phone.value = user?.photo
        }

    }

}