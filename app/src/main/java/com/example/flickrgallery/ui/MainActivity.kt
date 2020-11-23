package com.example.flickrgallery.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.flickrgallery.R
import com.example.flickrgallery.client.FlickrApiClient
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.repo.LocalRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var  database:Db
    private lateinit var localRepo: LocalRepoImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        database = Room.databaseBuilder(this, Db::class.java, "location-scout.db").build()

        localRepo = LocalRepoImpl(database)
        lifecycleScope.launch (Dispatchers.IO){
            // Example call to Flickr API client
            val wayPointPhotosResult = FlickrApiClient.service.listPhotosNearLocation(41.9575196,3.0333577)
            val wayPointPhotos = wayPointPhotosResult.photos.photo

            localRepo.insertAllPhotos( wayPointPhotos)
        }
    }
}