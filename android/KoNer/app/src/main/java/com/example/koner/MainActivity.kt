package com.example.koner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.koner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)




    } // onCreate

} // MainActivity