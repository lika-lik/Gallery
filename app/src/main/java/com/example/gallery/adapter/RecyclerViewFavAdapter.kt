package com.example.gallery.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import com.example.gallery.fragment.FavoritesFragment
import com.example.gallery.models.Picture
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat

class RecyclerViewFavAdapter(private val picturesList: List<Picture>,
                             private val onClickFavorite: OnClickFavorite,
                             private val favoriteController: FavoriteController)
    :RecyclerView.Adapter<RecyclerViewFavAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewPictureTitle: TextView = itemView.findViewById(R.id.textViewHeaderDetailRv)
        val imageViewPicture: ImageView = itemView.findViewById(R.id.imageViewDetailRv)
        val textViewData: TextView = itemView.findViewById(R.id.textViewDescrDetailRv)
        val textViewDescr: TextView = itemView.findViewById(R.id.textViewDescrDetailRv)
        val buttonFavorite: ToggleButton = itemView.findViewById(R.id.buttonFavoriteRecyclerViewFavorites)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_fav_item, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val picture = picturesList[position]
        Picasso.get()
            .load(picture.photoUrl)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.imageViewPicture)
        holder.textViewPictureTitle.text = picture.title
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        holder.textViewData.text = simpleDateFormat.format(picture.publicationDate)
        holder.textViewDescr.text = picture.content

        favoriteController.toggleButton = holder.buttonFavorite
        favoriteController.setButtonStyle(picture)
        holder.buttonFavorite.setOnCheckedChangeListener { button, isChecked ->
            favoriteController.toggleButton = button as ToggleButton
            onClickFavorite.onClick(favoriteController, picture, this)
        }
    }

    override fun getItemCount(): Int {
        return picturesList.size
    }
}