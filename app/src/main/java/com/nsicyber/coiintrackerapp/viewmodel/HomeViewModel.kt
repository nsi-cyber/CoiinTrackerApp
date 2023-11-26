package com.nsicyber.coiintrackerapp.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.nsicyber.coiintrackerapp.data.repository.TrackerRepository
import com.nsicyber.coiintrackerapp.model.response.CoinModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trackerRepository: TrackerRepository,
    private val application: Application
) : BaseViewModel()
{

    var coinList by mutableStateOf<List<CoinModel?>?>(null)
    var filteredCoinList by mutableStateOf<List<CoinModel?>?>(null)

    var searchTextField by mutableStateOf("")

    fun getCoinList() {
        apiCall(isBusyEnabled = true) {
            coinList = trackerRepository.getCoinList()
            filteredCoinList = coinList
        }

    }

    fun onSearchTextChanged(text: String) {
        searchTextField = text
        if (text.length >= 2)
            filteredCoinList = coinList?.filter { model ->
                model?.name?.contains(text) == true || model?.symbol?.contains(text) == true
            }
        else if (searchTextField.isEmpty())
            filteredCoinList = coinList
    }


}