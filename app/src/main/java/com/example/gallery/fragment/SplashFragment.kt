package com.example.gallery.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.gallery.R
import com.example.gallery.storage.SharedPrefManager

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPrefManager = SharedPrefManager.getInstance(requireContext())
        val direction = if (sharedPrefManager.user.id == -1){
            R.id.action_splashFragment_to_loginActivity
        }else{
            R.id.action_splashFragment_to_navigationActivity
        }

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(direction)
        }, 1000)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }
}