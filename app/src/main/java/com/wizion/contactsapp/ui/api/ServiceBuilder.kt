package com.wizion.contactsapp.ui.api

import android.os.Build
import android.util.Base64
import com.wizion.contactsapp.ui.main.other.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale
import java.util.concurrent.TimeUnit

/**
 * This class Create a Retrofit instance: Instantiate a Retrofit object by providing the base URL of the API you are accessing.
 */
object ServiceBuilder {


    // Create Logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val credentials = "${Constants.ACCOUNT_SID}:${Constants.AUTH_TOKEN}"
    val base64EncodedCredentials =
        "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)


    // Create a Custom Interceptor to apply Headers application wide
    private val headerInterceptor = object: Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            var request = chain.request()


            request = request.newBuilder()
                .addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language)
                .addHeader("Authorization", base64EncodedCredentials)
                .build()

            val response = chain.proceed(request)
            return response
        }
    }

    // Create OkHttp Client
    private val okHttp = OkHttpClient.Builder()
        .callTimeout(40, TimeUnit.SECONDS)
        .addInterceptor(headerInterceptor)
        .addInterceptor(logger)

    // Create Retrofit Builder
    private val builder = Retrofit.Builder().baseUrl(Constants.URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    // Create Retrofit Instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}