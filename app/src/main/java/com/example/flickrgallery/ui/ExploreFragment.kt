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
import com.example.flickrgallery.gps.GpsProvider
import com.example.flickrgallery.repo.GpsRepo
import com.example.flickrgallery.repo.GpsRepoImpl
import com.example.flickrgallery.repo.LocalRepo
import com.example.flickrgallery.repo.LocalRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExploreFragment : Fragment() {

    private lateinit var localRepo: LocalRepo
    private lateinit var gpsRepo: GpsRepo
    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var photosAdapter: PhotosAdapter

    // Falta controlar el "Denegar siempre"
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.proceedGettingUpdates()
        } else {
            Snackbar.make(
                binding.root,
                R.string.location_permission_denied,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(R.string.retry){
                requestLocationPermissionAndGetPhotos()
            }.show()
        }
    }

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

    private fun buildDependencies() {
        val database = Room.databaseBuilder(
            requireContext(),
            Db::class.java,
            "location-scout.db"
        ).build()
        localRepo = LocalRepoImpl(database)
        val gpsProvider = GpsProvider(requireContext())
        gpsRepo = GpsRepoImpl(gpsProvider)
    }



}