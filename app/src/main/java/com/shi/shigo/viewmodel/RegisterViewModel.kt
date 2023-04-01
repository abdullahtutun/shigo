package com.shi.shigo.viewmodel

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.shi.shigo.MobileApplication.Companion.appContext
import com.shi.shigo.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {
    var registerResponse: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun register(email: String, password: String, name: String){
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(appContext, R.string.register_is_success, Toast.LENGTH_SHORT).show()

                        val user = auth.currentUser
                        val profileUpdates = userProfileChangeRequest {
                            displayName = name
                        }
                        user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                            }
                        }
                        registerResponse.value = true

                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(appContext, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    registerResponse.value = false
                }
        } catch (e: Exception) {

        }

    }
}