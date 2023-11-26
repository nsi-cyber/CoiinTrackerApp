package com.nsicyber.coiintrackerapp.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


@Singleton
class FirebaseRepository @Inject constructor() {

    private var auth by mutableStateOf<FirebaseAuth?>(null)
    private var user by mutableStateOf<FirebaseUser?>(null)

    init {
        auth = Firebase.auth
    }

    fun getFirebaseUser(): FirebaseUser? {
        return user
    }

    fun setFirebaseUser() {
        user = auth?.currentUser
    }

    fun getUserId(): String? {
        return user?.uid
    }

    suspend fun signUp(email: String?, password: String?): Boolean {
        return suspendCancellableCoroutine { continuation ->
            auth?.createUserWithEmailAndPassword(email!!, password!!)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        setFirebaseUser()
                        continuation.resume(true)
                    } else {
                        continuation.resumeWithException(task.exception!!)
                    }
                }
        }
    }


    suspend fun login(email: String?, password: String?): Boolean {
        return suspendCancellableCoroutine { continuation ->
            auth?.signInWithEmailAndPassword(email!!, password!!)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        setFirebaseUser()
                        continuation.resume(true)
                    } else {
                        continuation.resumeWithException(task.exception!!)
                    }
                }
        }
    }





}