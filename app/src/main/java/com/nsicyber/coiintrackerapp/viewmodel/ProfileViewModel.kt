package com.nsicyber.coiintrackerapp.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nsicyber.coiintrackerapp.data.repository.UserRepository
import com.nsicyber.coiintrackerapp.model.response.CoinModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val application: Application
) : BaseViewModel()
{

    var coinList by mutableStateOf<List<CoinModel?>?>(null)


    fun getUserCoinList() {
        apiCall(isBusyEnabled = true) {
            coinList = userRepository.getUserLikes()
        }

    }



}