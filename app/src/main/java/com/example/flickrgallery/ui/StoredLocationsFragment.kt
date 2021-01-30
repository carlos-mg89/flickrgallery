package com.example.flickrgallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.example.data.repo.StoredLocationsRepo
import com.example.domain.StoredLocation
import com.example.flickrgallery.R
import com.example.flickrgallery.data.source.FusedLocationDataSource
import com.example.flickrgallery.data.source.StoredLocationsRoomDataSource
import com.example.flickrgallery.data.source.toFrameworkStoredLocation
import com.example.flickrgallery.databinding.StoredLocationsFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.ui.StoredLocationsFragmentDirections.Companion.actionStoredLocationsFragmentToStoredLocationFragment


class StoredLocationsFragment : Fragment() {

    private lateinit var binding: StoredLocationsFragmentBinding
    private lateinit var viewModel: StoredLocationsViewModel
    private lateinit var storedLocationsRepo: StoredLocationsRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.stored_locations_fragment, container ,false)
        setUpDependencies()
        initViewModel()
        subscribeUi()
        bindViewWithData()
        return binding.root
    }

    private fun setUpDependencies() {
        val database = Db.getDatabase(requireContext())
        val fusedLocationDataSource = FusedLocationDataSource(requireContext())
        val storedLocationsDataSource = StoredLocationsRoomDataSource(database)
        storedLocationsRepo = StoredLocationsRepo(storedLocationsDataSource, fusedLocationDataSource)
    }

    private fun initViewModel() {
        val factory = StoredLocationsViewModelFactory(storedLocationsRepo)
        viewModel = ViewModelProvider(this, factory).get()
        viewModel.startCollectingStoredLocations()
    }

    private fun subscribeUi() {
        viewModel.navigateToStoredLocation.observe(requireActivity()) { event ->
            event.getContentIfNotHandled()?.let {
                navigateToStoredLocation(it)
            }
        }
    }

    private fun bindViewWithData() {
        binding.viewModel = viewModel
        binding.recyclerView.adapter = StoredLocationsAdapter(viewModel)
        binding.lifecycleOwner = this

        viewModel.storedLocations.observe(requireActivity()) { storedLocations ->
            (binding.recyclerView.adapter as? StoredLocationsAdapter)?.storedLocations = storedLocations
        }
    }

    private fun navigateToStoredLocation(storedLocation: StoredLocation) {
        val frameworkStoredLocation = storedLocation.toFrameworkStoredLocation()
        findNavController()
            .navigate(actionStoredLocationsFragmentToStoredLocationFragment(frameworkStoredLocation))
    }
}