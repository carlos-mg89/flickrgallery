package com.example.flickrgallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.flickrgallery.databinding.StoredLocationsFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.repo.StoredLocationRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StoredLocationsFragment : Fragment() {

    private lateinit var binding: StoredLocationsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = StoredLocationsFragmentBinding.inflate(layoutInflater)

        val storedLocationsAdapter = StoredLocationsAdapter(emptyList())
        val database = Db.getDatabase(requireContext())
        val storedLocationRepo = StoredLocationRepoImpl(database)

        binding.recyclerView.adapter = storedLocationsAdapter

        lifecycleScope.launch (Dispatchers.IO) {
            val storedLocations = storedLocationRepo.findAll()
            storedLocationsAdapter.storedLocations = storedLocations

            withContext(Dispatchers.Main){
                storedLocationsAdapter.setItems(storedLocations)
            }
        }

        return binding.root
    }

}