package com.example.gallery.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.gallery.R
import com.example.gallery.fragment.HomeFragment
import com.example.gallery.fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, HomeFragment(),  HomeFragment::class.java.simpleName)
            .commit()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.home
        bottomNavigation.setOnItemSelectedListener { item ->
            var fragment: Fragment? = when (item.itemId) {
                R.id.home -> HomeFragment()
                R.id.favorites -> HomeFragment()
                R.id.profile -> ProfileFragment()
                else -> null
            }
            if (fragment != null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
                return@setOnItemSelectedListener true
            }
            return@setOnItemSelectedListener false
        }
    }
}