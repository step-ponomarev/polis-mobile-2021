package ru.mail.polis.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.mail.polis.db.firestore.ApartmentRepository
import ru.mail.polis.db.firestore.IApartmentRepository

class AddApartmentViewModel: ViewModel() {

    private val apartmentRepository: IApartmentRepository = ApartmentRepository()

    fun save(apartment: HashMap<String, String>) {
        apartmentRepository.save(apartment)
    }

    fun checkIfAccExist() {
        viewModelScope.launch(Dispatchers.IO) {
            apartmentRepository.getApartments(FirebaseAuth.getInstance().currentUser.email)
        }
    }
}