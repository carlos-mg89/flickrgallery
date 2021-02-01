package com.example.flickrgallery.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.MainItemBinding
import com.example.flickrgallery.model.Photo

class PhotosAdapter(
    var photoOnClickListener: (Photo) -> Unit
) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    var photos: List<Photo> by basicDiffUtil (
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.bindingInflate(R.layout.main_item,false))
    }

    override fun getItemCount(): Int {
        return photos.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.setPhoto(photos[position])
        holder.itemView.setOnClickListener{ photoOnClickListener(photos[position]) }
    }

    class ViewHolder(val binding: MainItemBinding) : RecyclerView.ViewHolder(binding.root)

}