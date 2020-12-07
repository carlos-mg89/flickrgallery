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
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.FragmentExploreBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.gps.GpsProvider
import com.example.flickrgallery.repo.GpsRepo
import com.example.flickrgallery.repo.GpsRepoImpl
import com.example.flickrgallery.repo.LocalRepo
import com.example.flickrgallery.repo.LocalRepoImpl
import com.google.android.material.snackbar.Snackbar

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
        viewModel.photos.observe(requireActivity()) { photos ->
            photosAdapter.setItems(photos)
        }
        viewModel.progressVisible.observe(requireActivity(),{
            val visibility = if (it) VISIBLE else GONE
            binding.exploreFragmentProgress.visibility = visibility
        })
    }

    private fun setupUi() {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        val activity = this.activity
        photosAdapter = PhotosAdapter(emptyList()) {
            (activity as MainActivityCommunicator).onPhotoClicked(it)
        }
        binding.recyclerview.adapter = photosAdapter
    }

    private fun buildDependencies() {
        val database = Db.getDatabase(requireContext().applicationContext)
        localRepo = LocalRepoImpl(database)
        val gpsProvider = GpsProvider(requireContext())
        gpsRepo = GpsRepoImpl(gpsProvider)
    }

    private fun buildViewModel(): MainViewModel {
        val factory = MainViewModelFactory(localRepo, gpsRepo)
        return ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }
}