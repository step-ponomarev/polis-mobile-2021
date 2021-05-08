package ru.mail.polis.viewModels

import android.util.Log
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddApartmentViewModel : ViewModel() {

    val liveData = MutableLiveData<LinearLayout>()

    fun addView(constraintLayout: ConstraintLayout, linearLayout: LinearLayout) {
        liveData.value = linearLayout
        liveData.value?.let {
            Log.d("&&&", "FROM LIVEDATA")
            it.addView(constraintLayout)
        }
    }

}