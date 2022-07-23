package com.example.gallery.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.content.ContentProviderCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import com.example.gallery.adapter.*
import com.example.gallery.databinding.FragmentFavoritesBinding
import com.example.gallery.databinding.FragmentHomeBinding
import com.example.gallery.models.Picture
import com.example.gallery.storage.SharedPrefManager

class FavoritesFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPrefManager = SharedPrefManager.getInstance(requireContext())

        val binding = FragmentFavoritesBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerViewFavorites
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.adapter = RecyclerViewFavAdapter(
            sharedPrefManager.favorites,
            OnClickFavoriteImp,
            FavoriteControllerIml(sharedPrefManager, requireContext()))

        return binding.root
    }
    object OnClickFavoriteImp: OnClickFavorite{
        override fun onClick(
            favoriteController: FavoriteController,
            picture: Picture,
            adapter: RecyclerViewFavAdapter
        ) {
            if (favoriteController.isFavorite(picture)){
                favoriteController.add(picture)
            }else{
                favoriteController.remove(picture)
            }
        }

    }
}