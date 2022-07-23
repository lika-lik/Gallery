package com.example.gallery.adapter

import android.content.Context
import android.widget.ToggleButton
import androidx.appcompat.content.res.AppCompatResources
import com.example.gallery.R
import com.example.gallery.models.Picture
import com.example.gallery.storage.SharedPrefManager

class FavoriteControllerIml(
    val sharedPrefManager: SharedPrefManager?,
    val context: Context
    ) : FavoriteController {
    override lateinit var toggleButton: ToggleButton

    override fun add(picture: Picture) {
        sharedPrefManager?.addFavorite(picture)
        toggleButton.background =
            AppCompatResources.getDrawable(context, R.drawable.ic_remove_from_favorites)
    }

    override fun remove(picture: Picture) {
        sharedPrefManager?.removeFavorite(picture)
        toggleButton.background =
            AppCompatResources.getDrawable(context, R.drawable.ic_add_to_favorite)
    }

    override fun setButtonStyle(picture: Picture) {
        if (isFavorite(picture)){
            toggleButton.background =
                AppCompatResources.getDrawable(context, R.drawable.ic_add_to_favorite)
        }else{
            toggleButton.background =
                AppCompatResources.getDrawable(context, R.drawable.ic_remove_from_favorites)
        }
    }

    override fun isFavorite(picture: Picture): Boolean {
        return sharedPrefManager?.favorites?.contains(picture) == false
    }

}