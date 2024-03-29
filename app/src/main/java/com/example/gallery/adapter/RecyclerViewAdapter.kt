package com.example.gallery.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import com.example.gallery.models.Picture
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val picturesList: List<Picture>,
                          private val listener: RecyclerViewListener,
                          private val favoriteController: FavoriteController) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewPictureTitle: TextView = itemView.findViewById(R.id.textViewPictureTitle)
        val imageViewPicture: ImageView = itemView.findViewById(R.id.imageViewPicture)
        val buttonFavorite: ToggleButton = itemView.findViewById(R.id.buttonFavoriteRecyclerViewFavorites)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val picture = picturesList[position]
        Picasso.get()
            .load(picture.photoUrl)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.imageViewPicture)
        holder.textViewPictureTitle.text = picture.title
        holder.imageViewPicture.setOnClickListener{
            listener.startActivity(picture)
        }
        favoriteController.toggleButton = holder.buttonFavorite
        favoriteController.setButtonStyle(picture)
        holder.buttonFavorite.setOnCheckedChangeListener { button, isChecked ->
            favoriteController.toggleButton = button as ToggleButton
            if (favoriteController.isFavorite(picture)){
                favoriteController.add(picture)
            }else{
                favoriteController.remove(picture)
            }
        }

    }

    override fun getItemCount(): Int {
        return picturesList.size
    }

}