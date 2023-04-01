package com.shi.shigo.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.shi.shigo.MobileApplication.Companion.appContext
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {
    var forgotPasswordResponse: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun resetPassword(mail: String) {
        auth.sendPasswordResetEmail(mail).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                forgotPasswordResponse.value = true
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(appContext, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            forgotPasswordResponse.value = false
        }
    }
}