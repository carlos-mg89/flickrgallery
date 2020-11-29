package com.example.flickrgallery.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.PhotoDetailsFragmentBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.LocalRepoImpl


class PhotoDetailsFragment : Fragment() {

    companion object {
        const val EXTRA_PHOTO = "PhotoDetailsFragment:photo"
    }

    private lateinit var viewModel: PhotoDetailsViewModel
    private lateinit var binding: PhotoDetailsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = PhotoDetailsFragmentBinding.inflate(inflater)

        val photo: Photo? = arguments?.getParcelable<Photo>(EXTRA_PHOTO)
        if(photo != null){
           Glide.with(this).load(photo.getMedium640Url()).into(binding.photo)
            binding.saveDataText.text = photo.savedDate.toString()
            binding.descriptionText.text = photo.title
            binding.commentsText.text = obtainCommentsPhoto().toString()
            binding.saveImageButton.setOnClickListener {
                viewModel.toggleSaveStatus()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = Db.getDatabase(requireContext().applicationContext)
        val localRepo = LocalRepoImpl(database)

        val factory = ViewModelFactory(localRepo)
        viewModel = ViewModelProvider(this,factory).get(PhotoDetailsViewModel::class.java)
        viewModel.prueba = "funciona"
        subscribeUi()

    }




    fun obtainCommentsPhoto(): ArrayList<String> {
        val comments = ArrayList<String>()
        comments.add("Pepe: Aliquam ex lectus, placerat eget rhoncus vel, convallis quis mi. Aenean neque nulla, suscipit non efficitur vitae, ultrices quis lectus. Nullam ultricies risus congue, rhoncus libero in, mattis eros. " +
                "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Nulla facilisi.")

        comments.add("Miguel: Sed mattis augue augue. Donec sit amet dapibus arcu. Pellentesque risus nisl, facilisis et fermentum in, sagittis et est. Suspendisse potenti. Donec metus elit, sagittis non tristique sit amet, imperdiet hendrerit ante. " +
                "Integer vestibulum sagittis erat a aliquam. " )
        return comments
    }

    fun subscribeUi(){
        viewModel.favoriteStatus.observe(this.viewLifecycleOwner,{isSaved ->
            val drawble = if(isSaved){
                ContextCompat.getDrawable(requireContext(),R.drawable.photo_saved)
            }else{
                ContextCompat.getDrawable(requireContext(),R.drawable.photo_no_saved)
            }
            binding.saveImageButton.setImageDrawable(drawble)

        })
    }

}