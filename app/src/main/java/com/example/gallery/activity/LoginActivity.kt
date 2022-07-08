package com.example.gallery.activity

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.TextFieldDefaults
import com.example.gallery.R
import com.example.gallery.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEnter.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                validation()
            }

        })
    }

    fun validation(): Boolean{
        val pattern = Regex("[+]?[78]?[() 0-9-]+")
        val login: String = binding.inputLogin.text.toString()
        val password: String = binding.inputPassword.text.toString()
        var validation: Boolean = true
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