package com.nsicyber.coiintrackerapp.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.nsicyber.coiintrackerapp.data.db.HawkUtils
import com.nsicyber.coiintrackerapp.data.repository.UserRepository
import com.nsicyber.coiintrackerapp.ui.components.home
import com.nsicyber.coiintrackerapp.ui.components.login
import com.nsicyber.coiintrackerapp.ui.components.navHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel() {


    init {
        Handler(Looper.getMainLooper()).postDelayed({
            openLogin()
        }, 1000)
    }

    fun openLogin() {
        navHostController!!.navigate(login)
    }

}