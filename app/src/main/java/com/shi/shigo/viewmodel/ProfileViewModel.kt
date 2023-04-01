package com.shi.shigo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val auth: FirebaseAuth, private val googleSignIn: GoogleSignInClient): ViewModel() {
    var logoutResponse: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun logout(){
        try {
            auth.signOut()
            googleSignIn.signOut()
            logoutResponse.value = true

        } catch (e: Exception) {

        }

    }
}