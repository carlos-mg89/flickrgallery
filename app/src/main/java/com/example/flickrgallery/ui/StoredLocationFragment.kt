package com.example.flickrgallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.flickrgallery.databinding.StoredLocationFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.StoredLocationRepo
import com.example.flickrgallery.repo.StoredLocationRepoImpl

class StoredLocationFragment : Fragment() {

    private lateinit var storedLocationRepo: StoredLocationRepo
    private lateinit var binding: StoredLocationFragmentBinding
    private lateinit var viewModel: StoredLocationViewModel
    private val args: StoredLocationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        buildDependencies()
        buildViewModel()
        setupUi()
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        viewModel.storedLocationUiState.observe(requireActivity()){
            val visibility = if (it.isProgressVisible) View.VISIBLE else View.GONE
            binding.progress.visibility = visibility
            binding.photoList.swapItems(it.photos)
        }
    }

    private fun setupUi() {
        binding = StoredLocationFragmentBinding.inflate(layoutInflater)
        binding.photoList.photoOnClickListener = { navigateToDetail(it) }
    }

    private fun navigateToDetail(photo: Photo) {
        findNavController().navigate(StoredLocationFragmentDirections.actionStoredLocationFragmentToPhotoDetailsFragment(photo))
    }

    private fun buildViewModel() {
        viewModel = ViewModelProvider(this).get(StoredLocationViewModel::class.java)
        viewModel.loadPhotos(args.storedLocationArg!!)
    }

    private fun buildDependencies() {
        val database = Db.getDatabase(requireContext().applicationContext)
        storedLocationRepo = StoredLocationRepoImpl(database)
    }
}