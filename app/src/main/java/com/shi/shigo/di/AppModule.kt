package com.shi.shigo.di

import android.app.Application
import android.content.Context
import com.android.billingclient.api.BillingClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.shi.shigo.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }

    @Singleton
    @Provides
    fun provideGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("795025967735-r5mn1oujmr1bqpf37c0or1nu5k5ho9bt.apps.googleusercontent.com")
            .requestEmail()
            .build()
    }

    @Singleton
    @Provides
    fun provideGoogleSignInClient(@ApplicationContext context: Context, gso: GoogleSignInOptions): GoogleSignInClient {
        return GoogleSignIn.getClient(context, gso)
    }
}