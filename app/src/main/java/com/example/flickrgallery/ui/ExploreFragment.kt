package com.example.flickrgallery.ui

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.*
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.FragmentExploreBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.gps.GpsProvider
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.*
import com.example.flickrgallery.ui.ExploreFragmentDirections.Companion.actionExploreFragmentToPhotoDetailsFragment
import com.google.android.material.snackbar.Snackbar

class ExploreFragment : Fragment() {


    private lateinit var gpsRepo: GpsRepo
    private lateinit var storedLocationRepo: StoredLocationRepo
    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewModel: ExploreViewModel


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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container ,false)
        bindViewWithData()
        setupUi()
        requestLocationPermissionAndGetPhotos()
        return binding.root
    }

    private fun requestLocationPermissionAndGetPhotos() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun bindViewWithData() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupUi() {
        binding.recyclerview.adapter = PhotosAdapter {
            navigateToDetail(it)
        }

        binding.exploreFragmentFab.setOnClickListener {
            viewModel.storeLocation(description = getRandomNumber().toString())
        }
    }

    private fun navigateToDetail(photo: Photo) {
        findNavController().navigate(actionExploreFragmentToPhotoDetailsFragment(photo))
    }

    private fun buildDependencies() {
        val database = Db.getDatabase(requireContext())
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