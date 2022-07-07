package com.example.gallery.models

data class User(
    val id: Int,
    val phone: String,
    val email: String,
    val city: String,
    val firstName: String,
    val lastName: String,
    val avatar: String,
    val about: String)