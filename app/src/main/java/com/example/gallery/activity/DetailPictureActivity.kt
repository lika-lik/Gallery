package com.example.gallery.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.gallery.R
import com.example.gallery.databinding.ActivityDetailPictureActivityBinding
import com.example.gallery.databinding.ActivityProfileBinding
import com.example.gallery.models.Picture
import com.example.gallery.models.User
import com.example.gallery.storage.SharedPrefManager
import com.squareup.picasso.Picasso
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailPictureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPictureActivityBinding
    private lateinit var picture: Picture

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPictureActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        picture = intent.extras?.getSerializable("pictureKey") as Picture
        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init(){
        binding.textViewHeaderDetail.text = picture.title
        binding.textViewDateDetail.text = dateFormat(picture.publicationDate)
        binding.textViewDescrDetail.text = picture.content

        Picasso.get()
            .load(picture.photoUrl)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.imageViewDetail)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateFormat(date: Long): String{
        return DateTimeFormatter.ISO_INSTANT.format(java.time.Instant.ofEpochSecond(date))
    }
}