package com.example.gallery.models

data class LoginResponse(
    val user_info: User,
    val token: String
)