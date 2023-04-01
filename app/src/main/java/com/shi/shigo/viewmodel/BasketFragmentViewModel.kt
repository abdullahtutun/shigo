package com.shi.shigo.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BasketFragmentViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {

}