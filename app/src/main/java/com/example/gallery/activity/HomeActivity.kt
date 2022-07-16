package com.example.gallery.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.adapter.RecyclerViewAdapter
import com.example.gallery.adapter.RecyclerViewListener
import com.example.gallery.api.RetrofitClient
import com.example.gallery.databinding.ActivityHomeBinding
import com.example.gallery.models.Picture
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
                    recyclerView.adapter = RecyclerViewAdapter(res, object: RecyclerViewListener{
                        override fun startActivity(picture: Picture) {
                            val newIntent =
                                Intent(applicationContext, DetailPictureActivity::class.java)
                            val mBundle = Bundle()
                            mBundle.putSerializable("pictureKey", picture)
                            newIntent.putExtras(mBundle)
                            startActivity(newIntent)
                        }
                    })
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