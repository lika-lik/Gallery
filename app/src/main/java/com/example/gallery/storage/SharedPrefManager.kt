package com.example.gallery.storage

import android.content.Context
import android.util.Log
import com.example.gallery.models.User

class SharedPrefManager(val mCtx: Context) {

    val user: User
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("phone", "") ?: "",
                sharedPreferences.getString("email", "") ?: "",
                sharedPreferences.getString("city", "") ?: "",
                sharedPreferences.getString("firstName", "") ?: "",
                sharedPreferences.getString("lastName", "") ?: "",
                sharedPreferences.getString("avatar", "") ?: "",
                sharedPreferences.getString("about", "") ?: "")
        }

    val token: String
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

            Log.d("MY123", "get2 string     ${sharedPreferences.getString("token", "")}")
            Log.d("MY123", "sharedPreferences2     ${sharedPreferences.toString()}")

            return sharedPreferences.getString("token", "") ?: ""
        }


    fun saveUser(user: User) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("id", user.id)
        editor.putString("phone", user.phone)
        editor.putString("email", user.email)
        editor.putString("city", user.city)
        editor.putString("firstName", user.firstName)
        editor.putString("lastName", user.lastName)
        editor.putString("avatar", user.avatar)
        editor.putString("about", user.about)

        editor.apply()

    }

    fun saveToken(token: String) {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        Log.d("MY123", "put string     $token")
        editor.apply()
        Log.d("MY123", "get1 string     ${sharedPreferences.getString("token", "")}")
        Log.d("MY123", "sharedPreferences1     ${sharedPreferences.toString()}")
    }


    fun clear(){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "gallery_shared_preff"
        private var mInstance: SharedPrefManager? = null

        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }
}