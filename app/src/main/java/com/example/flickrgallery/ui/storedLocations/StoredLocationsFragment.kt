package com.example.flickrgallery.ui.storedLocations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.domain.StoredLocation
import com.example.flickrgallery.data.source.toFrameworkStoredLocation
import com.example.flickrgallery.databinding.StoredLocationsFragmentBinding
import com.example.flickrgallery.ui.storedLocations.StoredLocationsFragmentDirections.Companion.actionStoredLocationsFragmentToStoredLocationFragment
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class StoredLocationsFragment : ScopeFragment() {

    private lateinit var binding: StoredLocationsFragmentBinding
    val viewModel: StoredLocationsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = StoredLocationsFragmentBinding.inflate(layoutInflater)

        setUpUi()
        subscribeUi()

        return binding.root
    }

    private fun setUpUi() {
        binding.recyclerView.adapter = StoredLocationsAdapter(viewModel)
    }

    private fun subscribeUi() {
        viewModel.startCollectingStoredLocations()
        viewModel.navigateToStoredLocation.observe(requireActivity()) { event ->
            event.getContentIfNotHandled()?.let {
                navigateToStoredLocation(it)
            }
        }
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