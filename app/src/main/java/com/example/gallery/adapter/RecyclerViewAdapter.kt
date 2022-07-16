package com.example.gallery.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import com.example.gallery.activity.DetailPictureActivity
import com.example.gallery.activity.HomeActivity
import com.example.gallery.models.Picture
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private val picturesList: List<Picture>,
                          private val listener: RecyclerViewListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textViewPictureTitle: TextView = itemView.findViewById(R.id.textViewPictureTitle)
        val imageViewPicture: ImageView = itemView.findViewById(R.id.imageViewPicture)

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
    }

    override fun getItemCount(): Int {
        return picturesList.size
    }

}