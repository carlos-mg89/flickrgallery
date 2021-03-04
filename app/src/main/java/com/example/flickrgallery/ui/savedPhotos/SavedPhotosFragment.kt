package com.example.flickrgallery.ui.savedPhotos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.domain.Photo
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.SavedPhotosFragmentBinding
import com.example.flickrgallery.ui.savedPhotos.SavedPhotosFragmentDirections.Companion.actionSavedPhotosFragmentToPhotoDetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.scope.ScopeFragment

class SavedPhotosFragment : ScopeFragment() {

    private lateinit var binding: SavedPhotosFragmentBinding
    val viewModel: SavedPhotosViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.saved_photos_fragment,container,false)
        return binding.root
    }

    private fun bindViewWhitData() {
        viewModel.startCollectingPhotos()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewWhitData()
        subscribeUi()
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