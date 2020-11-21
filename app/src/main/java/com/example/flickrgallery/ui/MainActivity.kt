package com.example.flickrgallery.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.flickrgallery.R
import com.example.flickrgallery.client.FlickrApiClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            // Example call to Flickr API client
            val wayPointPhotosResult = FlickrApiClient.service.listPhotosNearLocation(41.9575196,3.0333577)
            val wayPointPhotos = wayPointPhotosResult.photos.photo
        }
    }
}