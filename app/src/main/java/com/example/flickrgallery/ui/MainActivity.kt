package com.example.flickrgallery.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.ActivityMainBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.gps.GpsProvider
import com.example.flickrgallery.model.GpsSnapshot
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.GpsRepoImpl
import com.example.flickrgallery.repo.LocalRepoImpl
import com.example.flickrgallery.repo.StoredLocationRepoImpl
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface MainActivityCommunicator {
    fun onPhotoClicked(photo: Photo)
}

class MainActivity : AppCompatActivity(), MainActivityCommunicator {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var currentLocation: GpsSnapshot? = null

    private val requestPermissionLauncherToGetLocation = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
            if (isGranted) {
                setLocationListenerToObtainInitialPhotos()
            } else {
                Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getMainViewModel()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }

    private fun getMainViewModel(): MainViewModel {
        val database = Db.getDatabase(applicationContext)
        val localRepo = LocalRepoImpl(database)
        val factory = ViewModelFactory(localRepo)

        return ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    private fun setListeners() {
        setOnNavigationItemSelectedListener()
        setStoreLocationFabOnClickListener()
        setProgressVisibleObserver()
        requestLocationPermissionsSoExploreFragmentIsLoadedWithPhotos()
    }

    private fun setProgressVisibleObserver() {
        viewModel.progressVisible.observe(this) {
            binding.progressVisibleLayout.visibility = if(it) View.VISIBLE else View.GONE
        }
    }

    private fun requestLocationPermissionsSoExploreFragmentIsLoadedWithPhotos() {
        requestPermissionLauncherToGetLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        viewModel.photos.observe(this) {
            binding.bottomNavigation.selectedItemId = R.id.nav_explore
            viewModel.photos.removeObservers(this@MainActivity)
        }
    }

    private fun setOnNavigationItemSelectedListener() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> {
                    replaceFragmentContainerWith(ExploreFragment())
                    binding.storeLocationFab.visibility = View.VISIBLE
                    true
                }
                R.id.nav_stored_locations -> {
                    replaceFragmentContainerWith(StoredLocationsFragment())
                    binding.storeLocationFab.visibility = View.GONE
                    true
                }
                R.id.nav_favorite_photos -> {
                    binding.storeLocationFab.visibility = View.GONE
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

    private fun setStoreLocationFabOnClickListener() {
        binding.storeLocationFab.setOnClickListener {
            storeLocation()
        }
    }

    private fun storeLocation() {
        Toast.makeText(this, R.string.stored_location_success, Toast.LENGTH_LONG).show()
        lifecycleScope.launch(Dispatchers.IO) {
            currentLocation?.let {
                val database = Db.getDatabase(applicationContext)
                val storedLocationRepo = StoredLocationRepoImpl(database)

                val newStoredLocation = StoredLocation().apply {
                    latitude = it.latitude
                    longitude = it.longitude
                    description = "Location ${getRandomNumber()}"
                }

                storedLocationRepo.insert(newStoredLocation)
            }
        }
    }

    private fun setLocationListenerToObtainInitialPhotos() {
        val gpsProvider = GpsProvider(this)
        val gpsRepo = GpsRepoImpl(gpsProvider)
        gpsRepo.setAccurateLocationListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.setPhotosAt(it.latitude, it.longitude)
                currentLocation = it
            }
        }
    }

    private fun getRandomNumber(): Int {
        val min = 1
        val max = 100
        val randomDouble = Math.random() * (max - min + 1) + min
        return randomDouble.toInt()
    }

    override fun onPhotoClicked(photo: Photo) {
        val bundle = Bundle()
        val photoDetailsFragment = PhotoDetailsFragment()
        bundle.putParcelable(PhotoDetailsFragment.EXTRA_PHOTO,photo)
        photoDetailsFragment.arguments = bundle
        replaceFragmentContainerWith(photoDetailsFragment)
    }
}