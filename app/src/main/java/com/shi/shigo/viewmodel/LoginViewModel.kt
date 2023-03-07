package com.shi.shigo.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.shi.shigo.MobileApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {
    var loginResponse: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginResponse.value = true
                }
            }.addOnFailureListener {exception ->
                Toast.makeText(MobileApplication.appContext, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                loginResponse.value = false
            }
    }
}