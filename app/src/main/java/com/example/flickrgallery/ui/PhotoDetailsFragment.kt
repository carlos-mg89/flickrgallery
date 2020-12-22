package com.example.flickrgallery.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.PhotoDetailsFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepoImpl


class PhotoDetailsFragment : Fragment() {

    companion object {
        const val EXTRA_PHOTO = "PhotoDetailsFragment:photo"
    }

    private lateinit var photo: Photo
    private lateinit var viewModel: PhotoDetailsViewModel
    private lateinit var binding: PhotoDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        initViewModel()
        binding = DataBindingUtil.inflate(inflater, R.layout.photo_details_fragment, container, false)

        photo = arguments?.getParcelable(EXTRA_PHOTO)!!
        binding.lifecycleOwner = this
        binding.setPhoto(photo)
        binding.viewModel = viewModel

        return binding.root
    }

    private fun initViewModel() {
        val database = Db.getDatabase(requireContext().applicationContext)
        val photoRepo = PhotoRepoImpl(database)
        val factory = PhotoDetailsViewModelFactory(photoRepo)
        viewModel = ViewModelProvider(this, factory).get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUi()
        viewModel.checkIfPhotoExists(photo)
    }

    private fun subscribeUi() {
        viewModel.favoriteStatus.observe(this.viewLifecycleOwner) { isSaved ->
            val drawableRes = if (isSaved) {
                R.drawable.photo_saved
            } else {
                R.drawable.photo_no_saved
            }
            binding.saveImageFab.setImageResource(drawableRes)
        }
    }

}