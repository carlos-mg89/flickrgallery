package com.example.flickrgallery.ui.photoDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.domain.Photo
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.PhotoDetailsFragmentBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class PhotoDetailsFragment : ScopeFragment() {

    private lateinit var photo: Photo
    val viewModel: PhotoDetailsViewModel by viewModel()
    private lateinit var binding: PhotoDetailsFragmentBinding
    private val args: PhotoDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.photo_details_fragment, container, false)

        photo = args.photoArg!!
        binding.lifecycleOwner = this
        binding.setPhoto(photo)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeUi()
        viewModel.checkIfPhotoExists(photo)
    }

    private fun subscribeUi() {
        viewModel.favoriteStatus.observe(this.viewLifecycleOwner) { isSaved ->
            binding.saveImageFab.setImageResource(getFabDrawableRes(isSaved))
            binding.saveImageFab.contentDescription = getFabContentDescriptionRes(isSaved)
        }
    }

    private fun getFabDrawableRes(isSaved: Boolean): Int {
        return if (isSaved) {
            R.drawable.photo_saved
        } else {
            R.drawable.photo_no_saved
        }
    }

    private fun getFabContentDescriptionRes(isSaved: Boolean): String {
        val stringRes = if (isSaved) {
            R.string.photo_details_unsave_btn
        } else {
            R.string.photo_details_save_btn
        }
        return getString(stringRes)
    }

}