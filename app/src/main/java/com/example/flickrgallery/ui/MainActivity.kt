package com.example.flickrgallery.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.ActivityMainBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.gps.GpsProvider
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.GpsRepo
import com.example.flickrgallery.repo.GpsRepoImpl
import com.example.flickrgallery.repo.StoredLocationRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface MainActivityCommunicator{
    fun onPhotoClicked(photo:Photo)
}

class MainActivity : AppCompatActivity(), MainActivityCommunicator {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gpsRepo: GpsRepo
    private var navigationHistory = NavigationHistory()

    // TODO: 29/11/2020 Petición de permisos de forma simple
    private val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                storeLocation()
            } else {
                Toast.makeText(this, R.string.location_permission_denied, Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gpsProvider = GpsProvider(applicationContext)
        gpsRepo = GpsRepoImpl(gpsProvider)

        setOnNavigationItemSelectedListener()
        setStoreLocationFabOnClickListener()
    }

    private fun setOnNavigationItemSelectedListener() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_explore -> {
                    replaceFragmentContainerWith(ExploreFragment(), item.itemId)
                    binding.storeLocationFab.visibility = View.VISIBLE
                    true
                }
                R.id.nav_stored_locations -> {
                    replaceFragmentContainerWith(StoredLocationsFragment(), item.itemId)
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

        binding.bottomNavigation.selectedItemId = R.id.nav_explore
    }

    private fun replaceFragmentContainerWith(fragment: Fragment, navItemId: Int = R.id.nav_explore) {
        navigationHistory.pushItem(navItemId)
        supportFragmentManager
                .beginTransaction()
                .replace(binding.fragmentContainer.id, fragment, navItemId.toString())
                .addToBackStack(navItemId.toString())
                .commit()
    }

    private fun setStoreLocationFabOnClickListener() {
        binding.storeLocationFab.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun storeLocation() {
        Toast.makeText(this, R.string.stored_location_success, Toast.LENGTH_LONG).show()
        lifecycleScope.launch(Dispatchers.IO) {
            val database = Db.getDatabase(applicationContext)
            val storedLocationRepo = StoredLocationRepoImpl(database)
            val snapshot = gpsRepo.getActualPosition()

            val newStoredLocation = StoredLocation().apply {
                latitude = snapshot.latitude
                longitude = snapshot.longitude
                description = "Location ${getRandomNumber()}"
            }

            storedLocationRepo.insert(newStoredLocation)
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

    override fun onBackPressed() = with(navigationHistory) {
        if (!isEmpty()) {
            binding.bottomNavigation.selectedItemId = popLastSelected()
        }
        handleBackStackFragments()
    }

    private fun handleBackStackFragments() {
        val tag = currentFragment?.tag
        val backStackIsEmpty = supportFragmentManager.backStackEntryCount == 1
        val isStartFragment = tag == 0.toString()
        if (backStackIsEmpty && isStartFragment) {
            finish()
        } else {
            val fragmentPopped = supportFragmentManager.popBackStackImmediate(tag, 0)
            if (fragmentPopped) {
                binding.bottomNavigation.selectedItemId = tag?.toIntOrNull() ?: R.id.nav_explore
            } else {
                binding.bottomNavigation.selectedItemId = R.id.nav_explore
            }
        }
    }

    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.container)
}