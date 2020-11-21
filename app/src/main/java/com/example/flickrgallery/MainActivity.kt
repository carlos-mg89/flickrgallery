package com.example.flickrgallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flickrgallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerview.adapter = PhotosAdapter(
            listOf(
                Photo("photo1","https://loremflickr.com/320/240?random=1"),
                Photo("photo2","https://loremflickr.com/320/240?random=12"),
                Photo("photo3","https://loremflickr.com/320/240?random=3"),
                Photo("photo4","https://loremflickr.com/320/240?random=4"),
                Photo("photo5","https://loremflickr.com/320/240?random=5"),
                Photo("photo6","https://loremflickr.com/320/240?random=6"),
                Photo("photo7","https://loremflickr.com/320/240?random=7"),
                Photo("photo7","https://loremflickr.com/320/240?random=8"),
                Photo("photo8","https://loremflickr.com/320/240?random=9")
            )
        )
    }
}