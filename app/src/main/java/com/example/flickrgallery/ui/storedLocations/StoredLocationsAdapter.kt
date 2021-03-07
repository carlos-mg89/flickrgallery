package com.example.flickrgallery.ui.storedLocations

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.StoredLocation
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.StoredLocationsItemBinding
import com.example.flickrgallery.ui.common.basicDiffUtil

class StoredLocationsAdapter(
        private val viewModel: StoredLocationsViewModel
) :
    RecyclerView.Adapter<StoredLocationsAdapter.ViewHolder>() {

    private lateinit var binding: StoredLocationsItemBinding
    var storedLocations: List<StoredLocation> by basicDiffUtil (
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = StoredLocationsItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return storedLocations.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val storedLocation = storedLocations[position]
        holder.binding.container.setOnClickListener{
            viewModel.onStoredLocationClicked(storedLocation)
        }
        holder.binding.deleteBtn.setOnClickListener {
            viewModel.onStoredLocationDeleteClicked(storedLocation)
        }
        storedLocation.description = replaceBlankDescriptionWithLocalizedDefault(
            storedLocation.description,
            holder.binding.description.context
        )

        holder.bind(storedLocation)
    }

    private fun replaceBlankDescriptionWithLocalizedDefault(
        description: String,
        context: Context
    ): String {
        return if (description.isBlank()) {
            val string = context.getString(R.string.stored_locations_item_blank_description)
            string
        } else {
            description
        }
    }

    class ViewHolder(val binding: StoredLocationsItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(storedLocation: StoredLocation) {
            binding.savedDate.text = storedLocation.savedDateString
            binding.description.text = storedLocation.description
        }
    }
}