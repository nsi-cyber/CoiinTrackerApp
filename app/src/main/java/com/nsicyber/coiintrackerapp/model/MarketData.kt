package com.nsicyber.coiintrackerapp.model.response

data class MarketData(

    var price_change_percentage_24h: Float?=null,

    var current_price: CurrentPrice?=null,

)