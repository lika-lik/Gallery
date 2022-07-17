package com.example.gallery.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.gallery.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newIntent = Intent(this, LoginActivity::class.java)
        startActivity(newIntent)

    }
}


