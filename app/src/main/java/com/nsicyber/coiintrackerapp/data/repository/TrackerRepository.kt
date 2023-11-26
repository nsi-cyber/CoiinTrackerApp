package com.nsicyber.coiintrackerapp.data.repository

import com.nsicyber.coiintrackerapp.data.api.ApiService
import com.nsicyber.coiintrackerapp.model.CoinModel
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TrackerRepository @Inject constructor(private val apiService: ApiService) {


    suspend fun getCoinList(): List<CoinModel?>? {
        return apiService.getCoinList()
    }


    suspend fun getCoinDetail(id: String): CoinModel? {
        return apiService.getCoinDetail(id)
    }


}