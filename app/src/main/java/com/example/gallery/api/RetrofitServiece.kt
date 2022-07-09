package com.example.gallery.api

import com.example.gallery.models.LoginResponse
import retrofit2.Call
import retrofit2.http.*


interface RetrofitServices {

    @FormUrlEncoded
    @POST("auth/login")
    fun userLogin(
        @Field("phone") phone: String,
        @Field("password") password: String): Call<LoginResponse>
}