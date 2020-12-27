package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.PhotoDetailsFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.PhotoRepoImpl


class PhotoDetailsFragment : Fragment() {


    private lateinit var photo: Photo
    private lateinit var viewModel: PhotoDetailsViewModel
    private lateinit var binding: PhotoDetailsFragmentBinding
    private val args: PhotoDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = PhotoDetailsFragmentBinding.inflate(inflater)
        photo = args.photoArg!!
        Glide.with(this).load(photo.getMedium640Url()).into(binding.photo)
        binding.saveDataText.text = photo.savedDate.toString()
        binding.descriptionText.text = photo.title
        binding.commentsText.text = obtainCommentsPhoto().toString()
        binding.saveImageButton.setOnClickListener {
            viewModel.toggleSaveStatus(photo)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = Db.getDatabase(requireContext().applicationContext)
        val photoRepo = PhotoRepoImpl(database)
        val factory = PhotoDetailsViewModelFactory(photoRepo)
        viewModel = ViewModelProvider(this, factory).get(PhotoDetailsViewModel::class.java)
        subscribeUi()
        viewModel.getPhotoInitialState(photo)


    }

    private fun obtainCommentsPhoto() {
        //TODO: Get comments from api
    }


    private fun subscribeUi() {

        viewModel.favoriteStatus.observe(this.viewLifecycleOwner) { isSaved ->
            val drawable = if (isSaved) {

                ContextCompat.getDrawable(requireContext(), R.drawable.photo_saved)

            } else {

                ContextCompat.getDrawable(requireContext(), R.drawable.photo_no_saved)

            }
            binding.saveImageButton.setImageDrawable(drawable)

        }
    }


}