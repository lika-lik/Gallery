package com.example.gallery.models

import kotlinx.serialization.Serializable

@Serializable
data class Picture(
    val id: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val publicationDate: Long): java.io.Serializable