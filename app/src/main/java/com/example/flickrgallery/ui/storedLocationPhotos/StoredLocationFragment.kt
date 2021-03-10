package com.example.flickrgallery.ui.storedLocationPhotos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.domain.Photo
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.StoredLocationFragmentBinding
import com.example.flickrgallery.ui.common.PhotosAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.scope.ScopeFragment

class StoredLocationFragment : ScopeFragment() {

    private lateinit var binding: StoredLocationFragmentBinding
    val viewModel: StoredLocationViewModel by viewModel()
    private val args: StoredLocationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requestStoredLocationPhotos()
        binding = DataBindingUtil.inflate(inflater, R.layout.stored_location_fragment, container ,false)
        bindViewWithData()
        setupUi()
        return binding.root
    }

    private fun bindViewWithData() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun setupUi() {
        binding.recyclerview.adapter = PhotosAdapter {
            navigateToDetail(it)
        }
    }

    private fun navigateToDetail(photo: Photo) {
        findNavController().navigate(
            StoredLocationFragmentDirections.actionStoredLocationFragmentToPhotoDetailsFragment(
                photo
            )
        )
    }

    private fun requestStoredLocationPhotos() {
        viewModel.loadPhotos(args.storedLocationArg!!)
    }
}