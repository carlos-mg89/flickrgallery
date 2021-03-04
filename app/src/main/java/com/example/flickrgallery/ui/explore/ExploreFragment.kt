package com.example.flickrgallery.ui.explore

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.flickrgallery.R
import com.example.flickrgallery.data.source.toDomainPhoto
import com.example.flickrgallery.databinding.FragmentExploreBinding
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.ui.common.PhotosAdapter
import com.example.flickrgallery.ui.explore.ExploreFragmentDirections.Companion.actionExploreFragmentToPhotoDetailsFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class ExploreFragment : ScopeFragment() {

    private lateinit var binding: FragmentExploreBinding
    val viewModel: ExploreViewModel by viewModel()

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
        findNavController().navigate(actionExploreFragmentToPhotoDetailsFragment(photo.toDomainPhoto()))
    }

    private fun getRandomNumber(): Int {
        val min = 1
        val max = 100
        val randomDouble = Math.random() * (max - min + 1) + min
        return randomDouble.toInt()
    }
}