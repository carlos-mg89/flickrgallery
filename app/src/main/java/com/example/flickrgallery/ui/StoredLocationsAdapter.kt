package com.example.flickrgallery.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrgallery.databinding.StoredLocationsItemBinding
import com.example.flickrgallery.model.StoredLocation
import java.text.DateFormat
import java.util.*

class StoredLocationsAdapter(var storedLocations: List<StoredLocation>) :
    RecyclerView.Adapter<StoredLocationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StoredLocationsItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return storedLocations.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(storedLocations[position])
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