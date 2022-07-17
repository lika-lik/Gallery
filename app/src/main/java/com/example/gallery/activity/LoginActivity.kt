package com.example.gallery.activity

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gallery.R
import com.example.gallery.api.RetrofitClient
import com.example.gallery.databinding.ActivityLoginBinding
import com.example.gallery.models.LoginResponse
import com.example.gallery.storage.SharedPrefManager
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEnter.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                if(!validation()){
                    return
                }
                binding.progressBarEnter.visibility = View.VISIBLE
                binding.textViewButton.visibility = View.GONE
                saveUser()
            }
        })
    }

    fun saveUser(): Boolean{
        var error = false
        val call: Call<LoginResponse> = RetrofitClient.retrofitServices.userLogin("+71234567890", "qwerty")
        call.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showError(getString(R.string.internet_error))
                error = true
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {
                val body = response.body()
                if (body == null){
                    showError(getString(R.string.login_warning))
                    error = true
                }else{
                    val sharedPrefManager = SharedPrefManager.getInstance(application)
                    sharedPrefManager.saveUser(body.userInfo)
                    sharedPrefManager.saveToken(body.token)
                    val newIntent = Intent(applicationContext, MainActivity2::class.java)
                    startActivity(newIntent)
                    binding.progressBarEnter.visibility = View.GONE
                    binding.textViewButton.visibility = View.VISIBLE
                }
            }
        })
        return !error
    }

    fun showError(text: String){
        val snackbar = Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(getColor(R.color.error))
        snackbar.anchorView = binding.buttonEnter
        snackbar.show()
        binding.textFieldLogin.error = " "
        binding.textFieldPassword.error = " "
    }

    fun validation(): Boolean{
        val pattern = Regex("[+]?[78]?[() 0-9-]+")
        val login: String = binding.inputLogin.text.toString()
        val password: String = binding.inputPassword.text.toString()
        var validation = true
        if (login.isEmpty()) {
            binding.textFieldLogin.error = getString(R.string.empty_field_warning)
            validation = false
        }else if(!pattern.matches(login)){
            binding.textFieldLogin.error = getString(R.string.phone_format_warning)
            validation = false
        }

        if(password.isEmpty()){
            binding.textFieldPassword.error = getString(R.string.empty_field_warning)
            validation = false
        }else if (password.length < 6 || password.length > 255){
            binding.textFieldPassword.error = getString(R.string.password_length_warning)
            validation = false
        }

        if (validation) {
            binding.textFieldLogin.error = null
            binding.textFieldPassword.error = null
        }
        return validation
    }

}