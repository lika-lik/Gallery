package com.example.gallery.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token: String,
    @SerializedName("user_info")
    val userInfo: User
)