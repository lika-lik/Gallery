package com.example.gallery.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
    private var login = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEnter.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                login = binding.inputLogin.text.toString()
                password = binding.inputPassword.text.toString()

                if (!validation()) {
                    return
                }

                setButtonProgress(true)
                saveUser()
            }
        })
    }

    fun saveUser(): Boolean {
        var error = false

        val call: Call<LoginResponse> =
            RetrofitClient.retrofitServices.userLogin(formatLoginToPlainText(login), password)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showError(getString(R.string.internet_error))
                error = true
                setButtonProgress(false)
            }

            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {
                val body = response.body()
                if (body == null) {
                    showError(getString(R.string.login_warning))
                    error = true
                } else {
                    val sharedPrefManager = SharedPrefManager.getInstance(application)
                    sharedPrefManager.saveUser(body.userInfo)
                    sharedPrefManager.saveToken(body.token)
                    val newIntent = Intent(applicationContext, NavigationActivity::class.java)
                    startActivity(newIntent)
                }
                setButtonProgress(false)
            }
        })
        return !error
    }

    fun showError(text: String) {
        val snackBar = Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
        snackBar.setBackgroundTint(getColor(R.color.error))
        snackBar.anchorView = binding.buttonEnter
        snackBar.show()
        binding.textFieldLogin.error = " "
        binding.textFieldPassword.error = " "
    }

    fun validation(): Boolean {
        val pattern = Regex("[+]?[78]?[() 0-9-]+")
        var validation = true
        if (login.isEmpty()) {
            binding.textFieldLogin.error = getString(R.string.empty_field_warning)
            validation = false
        } else if (!pattern.matches(login)) {
            binding.textFieldLogin.error = getString(R.string.phone_format_warning)
            validation = false
        }

        if (password.isEmpty()) {
            binding.textFieldPassword.error = getString(R.string.empty_field_warning)
            validation = false
        } else if (password.length < 6 || password.length > 255) {
            binding.textFieldPassword.error = getString(R.string.password_length_warning)
            validation = false
        }

        if (validation) {
            binding.textFieldLogin.error = null
            binding.textFieldPassword.error = null
        }
        return validation
    }

    fun setButtonProgress(progress: Boolean) {
        binding.progressBarEnter.visibility = if (progress) {
            View.VISIBLE
        } else {
            View.GONE
        }
        binding.textViewButton.visibility = if (progress) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun formatLoginToPlainText(login: String): String {
        var res = "+7 $login"
        res = res.replace("(", "")
        res = res.replace(")", "")
        res = res.replace(" ", "")
        return res
    }
}