package com.example.gallery.adapter

import android.widget.ToggleButton
import com.example.gallery.models.Picture

interface FavoriteController {
    var toggleButton: ToggleButton
    fun add(picture: Picture)
    fun remove(picture: Picture)
    fun setButtonStyle(picture: Picture)
    fun isFavorite(picture: Picture): Boolean
}