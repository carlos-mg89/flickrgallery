package com.example.flickrgallery.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrgallery.databinding.StoredLocationsItemBinding
import com.example.flickrgallery.model.StoredLocation
import java.text.DateFormat
import java.util.*

class StoredLocationsAdapter(
        var storedLocations: List<StoredLocation>,
        private val storedLocationSeeLocationPhotosClickListener: (StoredLocation) -> Unit,
        private val storedLocationDeleteClickListener: (StoredLocation) -> Unit
) :
    RecyclerView.Adapter<StoredLocationsAdapter.ViewHolder>() {

    private lateinit var binding: StoredLocationsItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = StoredLocationsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return storedLocations.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storedLocations[position])
        binding.seePhotosBtn.setOnClickListener {
            storedLocationSeeLocationPhotosClickListener(storedLocations[position])
        }
        binding.deleteBtn.setOnClickListener {
            storedLocationDeleteClickListener(storedLocations[position])
            storedLocations = storedLocations.minus(storedLocations[position])
            notifyDataSetChanged()
        }
    }

    class ViewHolder(private val binding: StoredLocationsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(storedLocation: StoredLocation) {
            val dateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault()
            )
            binding.savedDate.text = dateFormat.format(storedLocation.savedDate)
            binding.description.text = storedLocation.description
        }
    }

    fun setItems(storedLocations: List<StoredLocation>) {
        this.storedLocations = storedLocations
        notifyDataSetChanged()
    }
}