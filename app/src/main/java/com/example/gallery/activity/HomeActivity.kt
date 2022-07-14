package com.example.gallery.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import com.example.gallery.RecyclerViewAdapter
import com.example.gallery.api.RetrofitClient
import com.example.gallery.databinding.ActivityHomeBinding
import com.example.gallery.databinding.ActivityProfileBinding
import com.example.gallery.models.LoginResponse
import com.example.gallery.models.Picture
import com.example.gallery.models.PicturesResponse
import com.example.gallery.models.User
import com.example.gallery.storage.SharedPrefManager
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = binding.recyclerViewGallery
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val sharedPrefManager = SharedPrefManager.getInstance(application)
        val call = RetrofitClient.retrofitServices.getPicture("Token ${sharedPrefManager.token}") // TODO: в настройки retrofit
        call.enqueue(object : Callback<List<Picture>> {
            override fun onFailure(call: Call<List<Picture>>, t: Throwable) {
                // TODO: onFailure
            }

            override fun onResponse(call: Call<List<Picture>>, response: Response<List<Picture>>
            ) {
                val res = response.body()
                if (res == null){
                    if (response.code() == 401){
                        loginError(sharedPrefManager)
                    }
                }else{
                    recyclerView.adapter = RecyclerViewAdapter(res)
                }
            }
        })
    }

    private fun loginError(sharedPrefManager: SharedPrefManager){
        val text = "Необходимо перезайти в свой аккаунт"
        val snackbar = Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
        snackbar.show()

        sharedPrefManager.clear()

        val newIntent = Intent(this, LoginActivity::class.java)
        startActivity(newIntent)
    }
}