package com.nsicyber.coiintrackerapp.data.api

import com.nsicyber.coiintrackerapp.model.CoinModel
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("coins/{id}")
    suspend fun getCoinDetail(@Path("id") id:String) : CoinModel?

    @GET("coins/list")
    suspend fun getCoinList() : List<CoinModel?>?


}
