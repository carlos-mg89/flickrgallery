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
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.StoredLocationsFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.StoredLocationRepoImpl
import com.example.flickrgallery.ui.StoredLocationsFragmentDirections.Companion.actionStoredLocationsFragmentToStoredLocationFragment

class StoredLocationsFragment : Fragment() {

    private lateinit var binding: StoredLocationsFragmentBinding
    private lateinit var viewModel: StoredLocationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.stored_locations_fragment, container ,false)
        initViewModel()
        subscribeUi()
        bindViewWithData()
        return binding.root
    }

    private fun initViewModel() {
        val database = Db.getDatabase(requireContext())
        val storedLocationRepo = StoredLocationRepoImpl(database)
        val factory = StoredLocationsViewModelFactory(storedLocationRepo)
        viewModel = ViewModelProvider(this, factory).get()
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
    }

    private fun navigateToStoredLocation(storedLocation: StoredLocation) {
        findNavController()
            .navigate(actionStoredLocationsFragmentToStoredLocationFragment(storedLocation))
    }
}