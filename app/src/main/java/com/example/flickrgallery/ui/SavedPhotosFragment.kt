package com.example.flickrgallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.SavedPhotosFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepoImpl
import com.example.flickrgallery.ui.SavedPhotosFragmentDirections.Companion.actionSavedPhotosFragmentToPhotoDetailsFragment

class SavedPhotosFragment : Fragment() {

    private lateinit var binding: SavedPhotosFragmentBinding
    private lateinit var viewModel: SavedPhotosViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.saved_photos_fragment,container,false)
        return binding.root
    }

    private fun bindViewWhitData() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        bindViewWhitData()
        subscribeUi()
    }

    private fun initViewModel() {
        val database = Db.getDatabase(requireContext().applicationContext)
        val photoRepo = PhotoRepoImpl(database)
        val factory = SavedPhotosViewModelFactory(photoRepo)
        viewModel = ViewModelProvider(this, factory).get(SavedPhotosViewModel::class.java)
    }

    private fun subscribeUi() {
        binding.recyclerView.adapter = SavedPhotosAdapter(
            onPhotoItemClicked = onSavedPhotoClicked(),
            onDeleteBtnClicked = onDeleteBtnClicked()
        )
    }

    private fun onSavedPhotoClicked(): (Photo) -> Unit = {
        findNavController().navigate(actionSavedPhotosFragmentToPhotoDetailsFragment(it))
    }

    private fun onDeleteBtnClicked(): (Photo) -> Unit = {
        viewModel.deleteSavedPhoto(it)
        Toast.makeText(
                activity, R.string.saved_photos_delete_success, Toast.LENGTH_LONG
        ).show()
    }
}