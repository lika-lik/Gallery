package com.example.gallery.dialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.DialogFragment
import com.example.gallery.R
import com.example.gallery.api.RetrofitClient
import com.example.gallery.databinding.FragmentDialogExistBinding
import com.example.gallery.storage.SharedPrefManager
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DialogExistFragment(
    private val loginActivityOpener: LoginActivityOpener
    ) : DialogFragment() {

    private lateinit var binding: FragmentDialogExistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDialogExistBinding.inflate(layoutInflater)
        init()
        return binding.root
    }

    private fun init() {
        var yesButton = binding.existYes
        var noButton = binding.existNo

        yesButton.setOnClickListener { close() }
        noButton.setOnClickListener { dialog?.dismiss() }
    }

    private fun close() {
        val sharedPrefManager =
            activity?.applicationContext?.let { SharedPrefManager.getInstance(it) }
        val call =
            RetrofitClient.retrofitServices.userLogout("Token ${sharedPrefManager?.token}")
        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.code() == 204 || response.code() == 401) {
                    userLogout(sharedPrefManager)
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                showError()
            }
        })
        dialog?.dismiss()
        requireActivity().findViewById<ProgressBar>(R.id.progress_bar_exist).visibility =
            View.VISIBLE
        requireActivity().findViewById<TextView>(R.id.textViewButtonExist).visibility =
            View.GONE
    }

    private fun userLogout(sharedPrefManager: SharedPrefManager?) {
        sharedPrefManager?.clear()
        loginActivityOpener.open()
    }

    private fun showError(){
        val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content),
            getString(R.string.internet_error),
            Snackbar.LENGTH_LONG)
        snackBar.setBackgroundTint(getColor(requireContext(), R.color.error))
        snackBar.anchorView = requireActivity().findViewById(R.id.bottom_navigation)
        snackBar.show()
    }

}