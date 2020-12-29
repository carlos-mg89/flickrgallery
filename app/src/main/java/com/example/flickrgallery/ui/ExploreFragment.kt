package com.example.flickrgallery.ui

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.FragmentExploreBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.gps.GpsProvider
import com.example.flickrgallery.repo.*
import com.example.flickrgallery.ui.ExploreFragmentDirections.Companion.actionExploreFragmentToPhotoDetailsFragment
import com.google.android.material.snackbar.Snackbar

class ExploreFragment : Fragment() {


    private lateinit var gpsRepo: GpsRepo
    private lateinit var storedLocationRepo: StoredLocationRepo
    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: ExploreViewModel
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
    ): View {
        buildDependencies()
        viewModel = buildViewModel()
        setupUi()
        subscribeUi()
        requestLocationPermissionAndGetPhotos()
        return binding.root
    }

    private fun requestLocationPermissionAndGetPhotos() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun subscribeUi() {
        viewModel.exploreUiState.observe(requireActivity()){
            val visibility = if (it.isProgressVisible) VISIBLE else GONE
            binding.exploreFragmentProgress.visibility = visibility
            binding.exploreFragmentFab.isEnabled = it.isFabEnabled
            photosAdapter.setItems(it.photos)
        }
    }

    private fun setupUi() {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        photosAdapter = PhotosAdapter(emptyList()) {
            findNavController().navigate(actionExploreFragmentToPhotoDetailsFragment(it))
        }
        binding.recyclerview.adapter = photosAdapter

        binding.exploreFragmentFab.setOnClickListener {
            viewModel.storeLocation(description = getRandomNumber().toString())
        }
    }

    private fun buildDependencies() {
        val database = Db.getDatabase(requireContext().applicationContext)
        val gpsProvider = GpsProvider(requireContext())
        gpsRepo = GpsRepoImpl(gpsProvider)
        storedLocationRepo = StoredLocationRepoImpl(database)
    }

    private fun buildViewModel(): ExploreViewModel {
        val factory = ExploreViewModelFactory(gpsRepo, storedLocationRepo)
        return ViewModelProvider(this, factory).get(ExploreViewModel::class.java)
    }

    private fun getRandomNumber(): Int {
        val min = 1
        val max = 100
        val randomDouble = Math.random() * (max - min + 1) + min
        return randomDouble.toInt()
    }
}