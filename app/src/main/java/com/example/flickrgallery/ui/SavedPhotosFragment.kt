package com.example.flickrgallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.DataBindingUtil

import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.SavedPhotosFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepoImpl

class SavedPhotosFragment : Fragment() {

    private lateinit var binding: SavedPhotosFragmentBinding
    private lateinit var viewModel: SavedPhotosViewModel
    private val savedPhotosAdapter = SavedPhotosAdapter(

            onSavedPhotoClicked(),
            onDeleteBtnClicked()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        //binding = SavedPhotosFragmentBinding.inflate(layoutInflater)
        binding  = DataBindingUtil.inflate(inflater,R.layout.saved_photos_fragment,container,false)
        bindViewWithData()
        binding.recyclerView.adapter = savedPhotosAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        //subscribeUi()
    }

    private fun initViewModel() {
        val database = Db.getDatabase(requireContext().applicationContext)
        val photoRepo = PhotoRepoImpl(database)
        val factory = SavedPhotosViewModelFactory(photoRepo)
        viewModel = ViewModelProvider(this, factory).get(SavedPhotosViewModel::class.java)
    }

    private fun subscribeUi() {
        viewModel.savedPhotos.observe(viewLifecycleOwner) {
            savedPhotosAdapter.setItems(it)
        }
    }
    private fun bindViewWithData(){
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun onSavedPhotoClicked(): (Photo) -> Unit = {
        (activity as MainActivityCommunicator).onPhotoClicked(it)
    }

    private fun onDeleteBtnClicked(): (Photo) -> Unit = {
        viewModel.deleteSavedPhoto(it)
        Toast.makeText(
                activity, R.string.saved_photos_delete_success, Toast.LENGTH_LONG
        ).show()
    }
}