package com.example.flickrgallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.StoredLocationsFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.StoredLocationRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoredLocationsFragment : Fragment() {

    private lateinit var binding: StoredLocationsFragmentBinding
    private lateinit var viewModel: StoredLocationsViewModel
    private val storedLocationsAdapter = StoredLocationsAdapter(
            emptyList(),
            getPhotosFromLocationToDisplayThem(),
            deleteStoredLocation()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = StoredLocationsFragmentBinding.inflate(layoutInflater)
        binding.recyclerView.adapter = storedLocationsAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        observeStoredLocations()
    }

    private fun initViewModel() {
        val database = Db.getDatabase(requireContext().applicationContext)
        val storedLocationRepo = StoredLocationRepoImpl(database)
        val factory = StoredLocationsViewModelFactory(storedLocationRepo)
        viewModel = ViewModelProvider(this, factory).get()
    }

    private fun observeStoredLocations() {
        viewModel.storedLocations.observe(viewLifecycleOwner) { storedLocations ->
            storedLocationsAdapter.setItems(storedLocations)
        }
    }

    private fun getPhotosFromLocationToDisplayThem(): (StoredLocation) -> Unit = {
        lifecycleScope.launch(Dispatchers.IO) {
            // TODO Pass the StoredLocation's latitude and longitude along with it's description
            //  onto the Explore Fragment so it loads the photos
        }
    }

    private fun deleteStoredLocation(): (StoredLocation) -> Unit = {
        viewModel.delete(it)
        Toast.makeText(
                activity, R.string.stored_location_delete_success, Toast.LENGTH_LONG
        ).show()
    }
}