package com.nsicyber.coiintrackerapp.data.repository

import com.google.firebase.auth.FirebaseUser
import com.nsicyber.coiintrackerapp.model.CoinModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firebaseRepository: FirebaseRepository
)
{
    suspend fun getUserLikes(): List<CoinModel?>? {
        return firebaseRepository.readDataFromFirestore().map { it.toObject(CoinModel::class.java) }
    }

    fun getUser():FirebaseUser?{
        return firebaseRepository.getFirebaseUser()
    }

    suspend fun userLogin(email: String?, password: String?) {
        firebaseRepository.login(email, password)
    }
    suspend fun userSignUp(email: String?, password: String?) {
        firebaseRepository.signUp(email, password)
    }

    suspend fun userAddData(data: CoinModel?):Boolean?{
        return firebaseRepository.addDataToFirestore(data)
    }

    suspend fun userRemoveData(id: String?):Boolean?{
        return  firebaseRepository.deleteDataFromFirestore(coinId=id)
    }


}