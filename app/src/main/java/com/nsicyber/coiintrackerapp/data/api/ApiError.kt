package com.nsicyber.coiintrackerapp.data.api

import java.lang.Exception


data class ApiException(
    override val message: String?,
    val code : String? = null
) : Exception(message)

data class UnauthorizedException(
    override val message: String?
) : Exception(message)

data class NetworkException(
    override val message: String?
) : Exception(message)