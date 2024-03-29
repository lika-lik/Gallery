package com.example.gallery.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gallery.R
import com.example.gallery.activity.DetailPictureActivity
import com.example.gallery.activity.LoginActivity
import com.example.gallery.adapter.FavoriteControllerIml
import com.example.gallery.adapter.RecyclerViewAdapter
import com.example.gallery.adapter.RecyclerViewListener
import com.example.gallery.api.RetrofitClient
import com.example.gallery.databinding.FragmentHomeBinding
import com.example.gallery.models.Picture
import com.example.gallery.storage.SharedPrefManager
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.inflate(layoutInflater)
        recyclerView = view.findViewById(R.id.recycler_view_gallery)
        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, 2)

        init()
    }

    private fun init(){
        val sharedPrefManager =
            activity?.applicationContext?.let { SharedPrefManager.getInstance(it) }
        val call =
            RetrofitClient.retrofitServices.getPicture("Token ${sharedPrefManager?.token}")
        call.enqueue(object : Callback<List<Picture>> {
            override fun onFailure(call: Call<List<Picture>>, t: Throwable) {
                loginError(sharedPrefManager)
            }

            override fun onResponse(
                call: Call<List<Picture>>, response: Response<List<Picture>>
            ) {
                val res = response.body()
                if (res == null) {
                    if (response.code() == 401) {
                        loginError(sharedPrefManager)
                    }
                } else {
                    recyclerView.adapter = RecyclerViewAdapter(
                        res,
                        object : RecyclerViewListener {
                            override fun startActivity(picture: Picture) {
                                openPicture(picture)
                            }
                        },
                        FavoriteControllerIml(sharedPrefManager, requireContext())
                    )
                }
            }
        })
    }

    fun openPicture(picture: Picture){
        val newIntent =
            Intent(activity, DetailPictureActivity::class.java)
        val mBundle = Bundle()
        mBundle.putSerializable("pictureKey", picture)
        newIntent.putExtras(mBundle)
        activity?.startActivity(newIntent)
    }

    private fun loginError(sharedPrefManager: SharedPrefManager?) {
        val text = getString(R.string.login_warning)
        val recyclerView: View = requireActivity().findViewById(R.id.recycler_view_gallery)
        val snackbar = Snackbar.make(recyclerView, text, Snackbar.LENGTH_LONG)
        snackbar.show()

        sharedPrefManager?.clear()

        val newIntent = Intent(activity, LoginActivity::class.java)
        activity?.startActivity(newIntent)
    }
}