package com.example.flickrgallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.StoredLocationsFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.StoredLocationRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoredLocationsFragment : Fragment() {

    private lateinit var binding: StoredLocationsFragmentBinding
    private lateinit var storedLocationRepo: StoredLocationRepoImpl
    private val storedLocationsAdapter = StoredLocationsAdapter(
            emptyList(),
            getPhotosFromLocationToDisplayThem(),
            deleteStoredLocation()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = StoredLocationsFragmentBinding.inflate(layoutInflater)
        storedLocationRepo = StoredLocationRepoImpl(Db.getDatabase(requireContext()))
        binding.recyclerView.adapter = storedLocationsAdapter

        loadStoredLocationsIntoAdapter()

        return binding.root
    }

    private fun loadStoredLocationsIntoAdapter() {
        lifecycleScope.launch (Dispatchers.IO) {
            val storedLocations = storedLocationRepo.findAll()
            storedLocationsAdapter.storedLocations = storedLocations

            withContext(Dispatchers.Main){
                storedLocationsAdapter.setItems(storedLocations)
            }
        }
    }

    private fun getPhotosFromLocationToDisplayThem(): (StoredLocation) -> Unit = {
        lifecycleScope.launch(Dispatchers.IO) {
            // TODO Pass the StoredLocation's latitude and longitude along with it's description
            //  onto the Explore Fragment so it loads the photos
        }
    }

    private fun deleteStoredLocation(): (StoredLocation) -> Unit =
            {
                lifecycleScope.launch(Dispatchers.IO) {
                    storedLocationRepo.delete(it)
                }
                Toast.makeText(
                        activity, R.string.stored_location_delete_success, Toast.LENGTH_LONG
                ).show()
            }
}