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



        val randomIntent = Intent(this, LoginActivity::class.java)
        startActivity(randomIntent)

//        val call: Call<LoginResponse> = RetrofitClient.retrofitServices.getUser("+71234567890", "qwerty")
//        call.enqueue(object : Callback<LoginResponse> {
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                Log.d("MY123", "onFailure")
//            }
//
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//
//                Log.d("MY123", "onResponse")
//                Log.d("MY123", response.body().toString())
//            }
//        })
//        Log.d("MY123", call.toString())


    }
}


