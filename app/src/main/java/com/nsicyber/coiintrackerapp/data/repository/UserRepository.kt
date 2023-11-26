package com.nsicyber.coiintrackerapp.data.repository

import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firebaseRepository: FirebaseRepository
)
{

    fun getUser(): FirebaseUser?{
        return firebaseRepository.getFirebaseUser()
    }

    suspend fun userLogin(email: String?, password: String?) {
        firebaseRepository.login(email, password)
    }
    suspend fun userSignUp(email: String?, password: String?) {
        firebaseRepository.signUp(email, password)
    }



}