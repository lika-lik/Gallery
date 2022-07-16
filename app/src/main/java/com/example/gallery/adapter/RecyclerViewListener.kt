package com.example.gallery.adapter

import com.example.gallery.models.Picture

interface RecyclerViewListener {
    fun startActivity(picture: Picture)
}