package com.example.flickrgallery.ui

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
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
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.LocalRepoImpl
import com.example.flickrgallery.repo.StoredLocationRepoImpl
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface MainActivityCommunicator {
    fun onPhotoClicked(photo: Photo)
}

class MainActivity : AppCompatActivity(), MainActivityCommunicator {

    companion object {
        private const val ACCEPTABLE_MINIMUM_LOCATION_ACCURACY = 10
        private const val SECONDS_TO_UPDATE_LOCATION = 5 * 1000L
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? = null

    private val requestPermissionLauncherToGetLocation = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
            if (isGranted) {
                setLocationRefresherPeriodically()
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
        requestLocationPermissionsSoExploreFragmentIsLoadedWithPhotos()
    }

    private fun requestLocationPermissionsSoExploreFragmentIsLoadedWithPhotos() {
        requestPermissionLauncherToGetLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        viewModel.photos.observe(this) {
            if (it.isNotEmpty()) {
                binding.bottomNavigation.selectedItemId = R.id.nav_explore
                viewModel.photos.removeObservers(this@MainActivity)
                fusedLocationClient.removeLocationUpdates(locationCallback)
            }
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

                viewModel.setPhotosAt(it.latitude, it.longitude)

                val newStoredLocation = StoredLocation().apply {
                    latitude = it.latitude
                    longitude = it.longitude
                    description = "Location ${getRandomNumber()}"
                }

                storedLocationRepo.insert(newStoredLocation)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setLocationRefresherPeriodically() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = getLocationCallback()
        fusedLocationClient.requestLocationUpdates(
                getLocationRequest(), locationCallback, null
        )
    }

    private fun getLocationRequest(): LocationRequest {
        return LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = SECONDS_TO_UPDATE_LOCATION
        }
    }

    private fun getLocationCallback() = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                if (isLocationAccurateEnough(location)) {
                    currentLocation = location
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.setPhotosAt(location.latitude, location.longitude)
                    }
                }
            }
        }
    }

    private fun isLocationAccurateEnough(location: Location?): Boolean {
        return location != null && location.accuracy < ACCEPTABLE_MINIMUM_LOCATION_ACCURACY
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