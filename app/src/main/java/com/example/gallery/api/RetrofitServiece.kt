package com.example.gallery.api

import com.example.gallery.models.LoginResponse
import com.example.gallery.models.Picture
import com.example.gallery.models.PicturesResponse
import retrofit2.Call
import retrofit2.http.*


interface RetrofitServices {

    @FormUrlEncoded
    @POST("auth/login")
    fun userLogin(
        @Field("phone") phone: String,
        @Field("password") password: String): Call<LoginResponse>

    @POST("auth/logout")
    fun userLogout(
        @Header("Authorization") token: String): Call<Unit>

    @GET("picture")
    fun getPicture(
        @Header("Authorization") token: String): Call<List<Picture>>
}