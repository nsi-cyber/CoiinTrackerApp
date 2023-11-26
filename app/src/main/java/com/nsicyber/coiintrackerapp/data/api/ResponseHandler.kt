package com.nsicyber.coiintrackerapp.data.api

import android.net.http.NetworkException
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.CancellationException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class NetworkResponseAdapter<S : Any>(
    private val successType: Type,
) : CallAdapter<S, Call<S>> {

    override fun adapt(call: Call<S>): Call<S> {
        return NetworkResponseCall(call)
    }

    override fun responseType(): Type = successType
}




internal class NetworkResponseCall<S : Any>(
    private val delegate: Call<S>,
) : Call<S> {

    override fun enqueue(callback: Callback<S>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()


                if (response.isSuccessful) {
                    if (body != null) {
                        if(response.code() in 200..300){
                            callback.onResponse(
                                this@NetworkResponseCall,
                                Response.success(body)
                            )
                        }
                        else if(response.code() == 401){
                            callback.onFailure(this@NetworkResponseCall,UnauthorizedException(response.errorBody().toString()))
                        }
                        else{
                            callback.onFailure(this@NetworkResponseCall,ApiException(response.errorBody().toString()))
                        }

                    } else {
                        callback.onFailure(this@NetworkResponseCall, ApiException("Generic Error"))
                    }
                } else {
                    if(response.code() == 404 || response.code() == 500){
                        callback.onFailure(this@NetworkResponseCall,CancellationException(response.message() ?: "Generic Error, Please Try Again Later"))
                    }
                    else
                        callback.onFailure(this@NetworkResponseCall,
                            NetworkException("Network Error")
                        )
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                callback.onFailure(this@NetworkResponseCall,throwable)
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun timeout(): Timeout = delegate.timeout()

    override fun clone() = NetworkResponseCall(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<S> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()
}
