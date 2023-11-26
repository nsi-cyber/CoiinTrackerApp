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
import com.nsicyber.coiintrackerapp.model.CoinModel
import com.nsicyber.coiintrackerapp.model.toMap
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


@Singleton
class FirebaseRepository @Inject constructor() {

    private var auth by mutableStateOf<FirebaseAuth?>(null)
    private var db by mutableStateOf<FirebaseFirestore?>(null)

    private var user by mutableStateOf<FirebaseUser?>(null)

    init {
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
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

    suspend fun addDataToFirestore(data: CoinModel?): Boolean? {
        return suspendCancellableCoroutine { continuation ->
            db?.collection(user?.uid ?: "")?.add(data!!)
                ?.addOnSuccessListener {
                    continuation.resume(true)
                }
                ?.addOnFailureListener {
                    continuation.resume(false)
                }
        }
    }

    suspend fun readDataFromFirestore(uid: String? = null): List<DocumentSnapshot> {
        return suspendCancellableCoroutine { continuation ->
            db?.collection(uid ?: user?.uid ?: "")?.get()
                ?.addOnSuccessListener { result ->
                    continuation.resume(result.documents)
                }
                ?.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        }
    }

    suspend fun deleteDataFromFirestore(uid: String? = null, coinId: String?): Boolean? {

        return suspendCancellableCoroutine { continuation ->


            db?.collection(uid ?: user?.uid ?: "")?.get()
                ?.addOnSuccessListener { result ->
                    result.documents.forEach { doc ->
                        if (doc.toObject(CoinModel::class.java)?.id == coinId) {
                            db?.collection(uid ?: user?.uid ?: "")
                                ?.document(doc.id)
                                ?.delete()
                                ?.addOnSuccessListener {
                                    continuation.resume(true)
                                }
                                ?.addOnFailureListener { e ->
                                    continuation.resumeWithException(e)
                                }
                        }
                    }
                }
                ?.addOnFailureListener {
                    continuation.resumeWithException(it)
                }


        }
    }

    suspend fun updateDataInFirestore(
        uid: String? = null,
        coinId: String?,
        newData: CoinModel
    ): Boolean? {
        return suspendCancellableCoroutine { continuation ->

            db?.collection(uid ?: user?.uid ?: "")?.get()
                ?.addOnSuccessListener { result ->
                    result.documents.forEach { doc ->
                        if (doc.toObject(CoinModel::class.java)?.id == coinId) {
                            db?.collection(uid ?: user?.uid ?: "")
                                ?.document(doc.id ?: "")
                                ?.update(newData.toMap())
                                ?.addOnSuccessListener {
                                    continuation.resume(true)
                                }
                                ?.addOnFailureListener { e ->
                                    continuation.resumeWithException(e)
                                }
                        }
                    }
                }
                ?.addOnFailureListener {
                    continuation.resumeWithException(it)
                }


        }
    }
}