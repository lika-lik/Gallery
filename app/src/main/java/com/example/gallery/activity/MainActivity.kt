package com.example.gallery.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.gallery.R
import com.example.gallery.api.RetrofitClient
import com.example.gallery.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newIntent = Intent(this, LoginActivity::class.java)
        startActivity(newIntent)
    }
}


