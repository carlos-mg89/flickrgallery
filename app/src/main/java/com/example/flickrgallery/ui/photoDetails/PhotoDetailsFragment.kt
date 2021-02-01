package com.example.flickrgallery.ui.photoDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.navArgs
import com.example.data.repo.PhotosRepo
import com.example.flickrgallery.R
import com.example.flickrgallery.data.source.PhotosFlickerDataSource
import com.example.flickrgallery.data.source.PhotosRoomDataSource
import com.example.flickrgallery.databinding.PhotoDetailsFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo
import com.example.usecases.GetSelectedPhoto
import com.example.usecases.MarkPhotoAsFavorite
import com.example.usecases.UnMarkPhotoAsFavorite


class PhotoDetailsFragment : Fragment() {


    private lateinit var photo: Photo
    private lateinit var photosRepo: PhotosRepo
    private lateinit var viewModel: PhotoDetailsViewModel
    private lateinit var binding: PhotoDetailsFragmentBinding
    private val args: PhotoDetailsFragmentArgs by navArgs()
    private lateinit var getSelectedPhoto: GetSelectedPhoto
    private lateinit var markPhotoAsFavorite: MarkPhotoAsFavorite
    private lateinit var unMarkPhotoAsFavorite: UnMarkPhotoAsFavorite


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        buildDependencies()
        initViewModel()
        binding = DataBindingUtil.inflate(inflater, R.layout.photo_details_fragment, container, false)

        photo = args.photoArg!!
        binding.lifecycleOwner = this
        binding.setPhoto(photo)
        binding.viewModel = viewModel

        return binding.root
    }

    private fun buildDependencies() {
        val database = Db.getDatabase(requireContext())
        val photosLocalDataSource = PhotosRoomDataSource(database)
        val photosRemoteDataSource = PhotosFlickerDataSource()
        photosRepo = PhotosRepo(photosLocalDataSource,photosRemoteDataSource)
        getSelectedPhoto = GetSelectedPhoto(photosRepo)
        markPhotoAsFavorite = MarkPhotoAsFavorite(photosRepo)
        unMarkPhotoAsFavorite = UnMarkPhotoAsFavorite(photosRepo)
    }

    private fun initViewModel() {
        val factory = PhotoDetailsViewModelFactory(getSelectedPhoto, markPhotoAsFavorite, unMarkPhotoAsFavorite)
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