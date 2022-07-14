package com.example.gallery.models

data class Picture(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: Long,
)