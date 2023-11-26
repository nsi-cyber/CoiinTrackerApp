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
class SignupViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val application: Application
) : BaseViewModel()
{



    var mail = mutableStateOf<String?>(null)
    var password1= mutableStateOf<String?>(null)
    var password2 = mutableStateOf<String?>(null)


    fun signUpApiCall() {
        if (mail.value != null && password1.value != null && password1.value==password2.value) {
            apiCall(isBusyEnabled = true) {
                userRepository.userSignUp(mail.value,password1.value)
                if (userRepository.getUser() != null) {
                    HawkUtils.setLastUser(userRepository.getUser()!!.uid)
                    navHostController!!.navigate(home)
                }
            }
        } else {
            Toast.makeText(
                application,
                "Lütfen bilgileri doğru doldurunuz",
                Toast.LENGTH_LONG
            ).show()
        }
    }



}