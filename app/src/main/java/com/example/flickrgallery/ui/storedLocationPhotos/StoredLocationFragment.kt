package com.example.flickrgallery.ui.storedLocationPhotos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.data.repo.PhotosRepo
import com.example.flickrgallery.R
import com.example.flickrgallery.data.source.PhotosFlickerDataSource
import com.example.flickrgallery.data.source.PhotosRoomDataSource
import com.example.flickrgallery.databinding.StoredLocationFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.ui.common.PhotosAdapter
import com.example.usecases.GetStoredLocationPhotos

class StoredLocationFragment : Fragment() {

    private lateinit var binding: StoredLocationFragmentBinding
    private lateinit var viewModel: StoredLocationViewModel
    private val args: StoredLocationFragmentArgs by navArgs()
    private lateinit var getStoredLocationPhotos: GetStoredLocationPhotos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        buildDependencies()
        buildViewModel()
        binding = DataBindingUtil.inflate(inflater, R.layout.stored_location_fragment, container ,false)
        bindViewWithData()
        setupUi()
        return binding.root
    }

    private fun buildDependencies() {
        val database = Db.getDatabase(requireContext())
        val photosLocalDataSource = PhotosRoomDataSource(database)
        val photosRemoteDataSource = PhotosFlickerDataSource()
        val photosRepo = PhotosRepo(photosLocalDataSource,photosRemoteDataSource)
        getStoredLocationPhotos = GetStoredLocationPhotos(photosRepo)
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

    private fun buildViewModel() {
        val factory = StoredLocationViewModelFactory(getStoredLocationPhotos)
        viewModel = ViewModelProvider(this, factory).get(StoredLocationViewModel::class.java)
        viewModel.loadPhotos(args.storedLocationArg!!)
    }
}