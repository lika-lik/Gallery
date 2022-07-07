package com.example.gallery.api

import com.example.gallery.retrofit.RetrofitClient

object RetrofitClient {
    private val BASE_URL = "https://pictures.chronicker.fun/api/"
    val retrofitServices: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}