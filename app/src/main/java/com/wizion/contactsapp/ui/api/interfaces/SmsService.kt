package com.wizion.contactsapp.ui.api.interfaces

import com.wizion.contactsapp.ui.api.ApiConstants
import com.wizion.contactsapp.ui.main.models.ReceiveSmsListResponse
import com.wizion.contactsapp.ui.main.models.ResponseSentSms
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Endpoint interfaces define the API endpoints or services that you can access and interact with using the Retrofit library
 */
interface SmsService {

    @GET(ApiConstants.receiveSMSList)
    suspend fun receiveSmsList() : Response<ReceiveSmsListResponse>

    @FormUrlEncoded
    @POST(ApiConstants.receiveSMSList)
    suspend fun sendSms(@Field("From") From : String,@Field("To") To : String,@Field("Body") Body : String) : Response<ResponseSentSms>

}