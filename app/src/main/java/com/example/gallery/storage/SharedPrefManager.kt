package com.example.gallery.storage

import android.content.Context
import com.example.gallery.models.Picture
import com.example.gallery.models.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class SharedPrefManager(val mCtx: Context) {

    private val gson = Gson()
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
            return sharedPreferences.getString("token", "") ?: ""
        }

    val favorites: MutableList<Picture>
        get() {
            val sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            val favoritesGson = sharedPreferences.getString("favorites", "") ?: ""
            val listType: Type = object : TypeToken<MutableList<Picture>>() {}.type
            return gson.fromJson(favoritesGson, listType) ?: listOf<Picture>().toMutableList()
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

    fun addFavorite(picture: Picture){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val list = favorites
        list.add(0, picture)
        editor.putString("favorites", (gson.toJson(list)))
        editor.commit()

    }

    fun removeFavorite(picture: Picture){
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val list = favorites
        list.remove(picture)
        editor.putString("favorites", (gson.toJson(list)))
        editor.commit()
    }

    fun saveToken(token: String) {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.apply()
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