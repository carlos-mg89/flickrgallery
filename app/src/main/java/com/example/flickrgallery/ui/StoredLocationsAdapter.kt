package com.example.flickrgallery.ui

import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.StoredLocationsItemBinding
import com.example.flickrgallery.model.StoredLocation
import com.example.flickrgallery.ui.common.basicDiffUtil
import com.example.flickrgallery.ui.common.bindingInflate

class StoredLocationsAdapter(
        private val viewModel: StoredLocationsViewModel
) :
    RecyclerView.Adapter<StoredLocationsAdapter.ViewHolder>() {

    var storedLocations: List<StoredLocation> by basicDiffUtil (
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.bindingInflate(R.layout.stored_locations_item, false))
    }

    override fun getItemCount(): Int {
        return storedLocations.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val storesLocation = storedLocations[position]
        holder.binding.storedLocation = storesLocation
        holder.binding.container.setOnClickListener{
            viewModel.onStoredLocationClicked(storesLocation)
        }
        holder.binding.deleteBtn.setOnClickListener {
            viewModel.onStoredLocationDeleteClicked(storesLocation)
        }
    }

    class ViewHolder(val binding: StoredLocationsItemBinding): RecyclerView.ViewHolder(binding.root)
}

@BindingAdapter("storedLocations")
fun RecyclerView.setStoredLocations(storedLocations: List<StoredLocation>?) {
    (adapter as? StoredLocationsAdapter)?.let {
        it.storedLocations = storedLocations ?: emptyList()
    }
}