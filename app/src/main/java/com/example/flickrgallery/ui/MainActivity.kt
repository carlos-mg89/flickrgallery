package com.example.flickrgallery.ui


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.ActivityMainBinding
import com.example.flickrgallery.db.Db
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.repo.StoredLocationRepoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface MainActivityCommunicator{
    fun onPhotoClicked(photo:Photo)

}
class MainActivity : AppCompatActivity(),MainActivityCommunicator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnNavigationItemSelectedListener()
        setStoreLocationFabOnClickListener()

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

        binding.bottomNavigation.selectedItemId = R.id.nav_explore
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
            Toast.makeText(this, R.string.stored_location_success, Toast.LENGTH_LONG).show()
            lifecycleScope.launch(Dispatchers.IO) {
                val database = Db.getDatabase(applicationContext)
                val storedLocationRepo = StoredLocationRepoImpl(database)

                // TODO Get real location and ask user to enter a description for the new location
                val newStoredLocation = StoredLocation().apply {
                    latitude = 42.1115775
                    longitude = 3.1304088
                    description = "L'Escala"
                }

                storedLocationRepo.insert(newStoredLocation)
            }
        }
    }

    override fun onPhotoClicked(photo: Photo) {
        val bundle = Bundle()
        val photoDetailsFragment = PhotoDetailsFragment()
        bundle.putParcelable(PhotoDetailsFragment.EXTRA_PHOTO,photo)
        photoDetailsFragment.arguments = bundle
        replaceFragmentContainerWith(photoDetailsFragment)
    }

}