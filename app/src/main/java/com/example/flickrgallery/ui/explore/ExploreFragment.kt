package com.example.flickrgallery.ui.explore

import EspressoIdlingResource
import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.domain.Photo
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.FragmentExploreBinding
import com.example.flickrgallery.databinding.InputTextDialogBinding
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
        EspressoIdlingResource.increment()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false)
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
            showNewLocationInputDialog(onSaveDescriptionClicked = { description ->
                viewModel.storeLocation(description = description)
            })
        }
    }

    private fun navigateToDetail(photo: Photo) {
        findNavController().navigate(actionExploreFragmentToPhotoDetailsFragment(photo))
    }

    private fun showNewLocationInputDialog(onSaveDescriptionClicked: (String) -> Unit){
        val alertDialog: AlertDialog = requireActivity().let {
            val binding: InputTextDialogBinding = DataBindingUtil.inflate(
                it.layoutInflater, R.layout.input_text_dialog, null,false
            )
            val builder = AlertDialog.Builder(it)

            builder.apply {
                setTitle(R.string.explore_location_dialog_input_title)
                setView(binding.root)
                setPositiveButton(R.string.save) { _, _ ->
                    onSaveDescriptionClicked(binding.inputText.text.toString())
                }
                setNegativeButton(R.string.cancel) { _, _ ->
                    // Only dismiss
                }
            }
            builder.create()
        }
        alertDialog.show()
    }
}