package com.nsicyber.coiintrackerapp.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nsicyber.coiintrackerapp.data.repository.TrackerRepository
import com.nsicyber.coiintrackerapp.data.repository.UserRepository
import com.nsicyber.coiintrackerapp.model.response.CoinModel
import com.nsicyber.coiintrackerapp.ui.components.navHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val trackerRepository: TrackerRepository,
    private val application: Application
) : BaseViewModel() {

    var coinDetail by mutableStateOf<CoinModel?>(null)


    fun getCoinDetail(id: String?) {
        apiCall(isBusyEnabled = true) {
            coinDetail = trackerRepository.getCoinDetail(id!!)
        }

    }





}