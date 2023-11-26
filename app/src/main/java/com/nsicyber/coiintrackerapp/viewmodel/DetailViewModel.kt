package com.nsicyber.coiintrackerapp.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nsicyber.coiintrackerapp.data.repository.TrackerRepository
import com.nsicyber.coiintrackerapp.data.repository.UserRepository
import com.nsicyber.coiintrackerapp.model.CoinModel
import com.nsicyber.coiintrackerapp.service.BackgroundTrackerService
import com.nsicyber.coiintrackerapp.ui.components.navHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val trackerRepository: TrackerRepository,
    private val userRepository: UserRepository,
    private val application: Application,

) : BaseViewModel() {

    var coinDetail by mutableStateOf<CoinModel?>(null)
    var bottomSheetState by mutableStateOf<Boolean>(false)
    var isUserFavorited by mutableStateOf<Boolean>(false)


    fun getCoinDetail(id: String?) {
        apiCall(isBusyEnabled = true) {
            coinDetail = trackerRepository.getCoinDetail(id!!)
        }

    }



    fun addToFavorite(model: CoinModel?) {
        apiCall(isBusyEnabled = true) {
            userRepository.userAddData(model)?.let {
                if (it == true)
                    setSuccessDialogState(true, "Coin Added to Favorites") {

                        navHostController!!.popBackStack()
                    }
            }
        }
    }

    fun isUserFavorited(id:String?) {
        apiCall(isBusyEnabled = true) {

            if (!userRepository.getUserLikes()?.filter { model ->
                    model?.id == id
                }.isNullOrEmpty()
            )
                isUserFavorited = true
        }
    }


    fun removeFromFavorite() {
        apiCall(isBusyEnabled = true) {
            userRepository.userRemoveData(coinDetail?.id)?.let {
                if (it == true)
                    setSuccessDialogState(true, "Coin Removed from Favorites") {
                        navHostController!!.popBackStack()
                    }
            }
        }


    }



}