package com.nsicyber.coiintrackerapp.model.response

data class CoinModel(

    var name: String? = null,

    var symbol: String? = null,

    var hashing_algorithm: String? = null,

    var description: Description? = null,

    var image: Image? = null,

    var categories: List<String>? = null,

    var id: String? = null,

    var last_updated: String? = null,

    var market_data: MarketData? = null,

    var isLikedByUser: Boolean = false,

    var reloadPerHour: Int? = null





)


fun CoinModel?.toMap(): Map<String, Any?> {
    return mapOf(
        "name" to this?.name,
        "symbol" to this?.symbol,
        "hashing_algorithm" to this?.hashing_algorithm,
        "description" to this?.description,
        "image" to this?.image,
        "categories" to this?.categories,
        "id" to this?.id,
        "last_updated" to this?.last_updated,
        "market_data" to this?.market_data,
        "isLikedByUser" to this?.isLikedByUser,
        "reloadPerHour" to this?.reloadPerHour,

        )
}