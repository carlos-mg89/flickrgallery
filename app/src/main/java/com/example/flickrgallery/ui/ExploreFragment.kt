package com.example.flickrgallery.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.flickrgallery.client.FlickrApiClient
import com.example.flickrgallery.databinding.FragmentExploreBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.LocalRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreFragment : Fragment() {

    private lateinit var  database:Db
    private lateinit var localRepo: LocalRepoImpl
    private lateinit var binding: FragmentExploreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        val activity = this.activity
        val photosAdapter = PhotosAdapter(emptyList()) {
            (activity as MainActivityCommunicator).onPhotoClicked(it)
        }


        binding.recyclerview.adapter = photosAdapter
        database = Room.databaseBuilder(requireContext(), Db::class.java, "location-scout.db").build()

        localRepo = LocalRepoImpl(database)
        lifecycleScope.launch (Dispatchers.IO){
            // Example call to Flickr API client
            val wayPointPhotosResult = FlickrApiClient.service.listPhotosNearLocation(41.9575196,3.0333577)
            val wayPointPhotos = wayPointPhotosResult.photos.photo

            localRepo.insertAllPhotos(wayPointPhotos)
            photosAdapter.photos = wayPointPhotos

            withContext(Dispatchers.Main){
                photosAdapter.setItems(wayPointPhotos)
            }
        }
        return binding.root
    }



}