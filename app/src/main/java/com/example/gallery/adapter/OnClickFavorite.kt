package com.example.gallery.adapter

import com.example.gallery.models.Picture

interface OnClickFavorite {
    fun onClick(favoriteController: FavoriteController, picture: Picture, adapter: RecyclerViewFavAdapter)
}