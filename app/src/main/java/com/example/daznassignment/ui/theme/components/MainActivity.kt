package com.example.daznassignment.ui.theme.components

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.daznassignment.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    var homeScreenFragment: HomeScreenFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeScreenFragment = HomeScreenFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, homeScreenFragment!!)
            .commitNow()
    }
}