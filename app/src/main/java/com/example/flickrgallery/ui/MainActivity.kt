package com.example.flickrgallery.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.ActivityMainBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.gps.GpsProvider
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.repo.GpsRepoImpl
import com.example.flickrgallery.repo.PhotoRepoImpl
import com.example.flickrgallery.repo.StoredLocationRepoImpl

interface MainActivityCommunicator {
    fun onPhotoClicked(photo: Photo)
}

class MainActivity : AppCompatActivity(), MainActivityCommunicator {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ExploreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getMainViewModel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        binding.bottomNavigation.selectedItemId = R.id.nav_explore
    }

    private fun getMainViewModel(): ExploreViewModel {
        val database = Db.getDatabase(applicationContext)
        val photoRepo = PhotoRepoImpl(database)
        val gpsProvider = GpsProvider(applicationContext)
        val gpsRepo = GpsRepoImpl(gpsProvider)
        val storedLocationRepo = StoredLocationRepoImpl(database)
        val factory = ExploreViewModelFactory(photoRepo, gpsRepo, storedLocationRepo)

        return ViewModelProvider(this, factory).get(ExploreViewModel::class.java)
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
}