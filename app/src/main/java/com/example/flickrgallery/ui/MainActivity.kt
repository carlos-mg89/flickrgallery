package com.example.flickrgallery.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.ActivityMainBinding
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.model.StoredLocation

interface MainActivityCommunicator {
    fun onPhotoClicked(photo: Photo)
    fun onStoredLocationClicked(storedLocation: StoredLocation)
}

class MainActivity : AppCompatActivity(), MainActivityCommunicator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        binding.bottomNavigation.selectedItemId = R.id.nav_explore
    }

    private fun setListeners() {
        setOnNavigationItemSelectedListener()
    }

    private fun setOnNavigationItemSelectedListener() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> {
                    replaceFragmentContainerWith(ExploreFragment())
                    true
                }
                R.id.nav_stored_locations -> {
                    replaceFragmentContainerWith(StoredLocationsFragment())
                    true
                }
                R.id.nav_favorite_photos -> {
                    replaceFragmentContainerWith(SavedPhotosFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragmentContainerWith(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainer.id, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun onPhotoClicked(photo: Photo) {
        val bundle = Bundle()
        val photoDetailsFragment = PhotoDetailsFragment()
        bundle.putParcelable(PhotoDetailsFragment.EXTRA_PHOTO,photo)
        photoDetailsFragment.arguments = bundle
        replaceFragmentContainerWith(photoDetailsFragment)
    }

    override fun onStoredLocationClicked(storedLocation: StoredLocation) {
        val exploreFragment = ExploreFragment()
        exploreFragment.arguments = Bundle().apply {
            putParcelable(ExploreFragment.EXTRA_STORED_LOCATION, storedLocation)
        }
        replaceFragmentContainerWith(exploreFragment)
    }
}