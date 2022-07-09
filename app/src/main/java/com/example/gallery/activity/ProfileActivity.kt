package com.example.gallery.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gallery.R
import com.example.gallery.databinding.ActivityProfileBinding
import com.example.gallery.models.User
import com.example.gallery.storage.SharedPrefManager
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = SharedPrefManager.getInstance(application).user
        init()
    }

    private fun init(){
        binding.textViewfFirstName.text = user.firstName
        binding.textViewLastName.text = user.lastName
        binding.textViewAbout.text = "\"${user.about}\""
        binding.textViewCity.text = user.city
        binding.textViewPhone.text = phoneFormat(user.phone)
        binding.textViewEmail.text = user.email
        Picasso.get()
            .load(user.avatar)
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.imageViewAvatar)
    }

    private fun phoneFormat(phone: String) = buildString {
        append(phone[0])
        append(phone[1])
        append(" ")
        append("(")
        append(phone[2])
        append(phone[3])
        append(phone[4])
        append(")")
        append(" ")
        append(phone[5])
        append(phone[6])
        append(phone[7])
        append(" ")
        append(phone[8])
        append(phone[9])
        append(" ")
        append(phone[10])
        append(phone[11])
    }
}