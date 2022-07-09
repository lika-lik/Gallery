package com.example.gallery.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user_info")
    val userInfo: User,
    val token: String
)