package com.nsicyber.coiintrackerapp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.nsicyber.coiintrackerapp.data.db.HawkUtils
import com.nsicyber.coiintrackerapp.data.repository.UserRepository
import com.nsicyber.coiintrackerapp.ui.components.home
import com.nsicyber.coiintrackerapp.ui.components.navHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val application: Application
) : BaseViewModel() {




    var mail = mutableStateOf<String?>(null)
    var password = mutableStateOf<String?>(null)


    fun loginApiCall() {
        if (mail.value != null && password.value != null) {
            apiCall(isBusyEnabled = true) {

                userRepository.userLogin(mail.value,password.value)

                if (userRepository.getUser() != null) {
                    HawkUtils.setLastUser(userRepository.getUser()!!.uid)
                    navHostController!!.navigate(home)
                }
            }
        } else {
            Toast.makeText(
                application,
                "Boş Bırakmayınız",
                Toast.LENGTH_LONG
            ).show()
        }
    }



}