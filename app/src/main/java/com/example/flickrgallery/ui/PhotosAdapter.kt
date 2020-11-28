package com.example.flickrgallery.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickrgallery.databinding.MainItemBinding
import com.example.flickrgallery.model.Photo

class PhotosAdapter(
    var photos: List<Photo>,
    var photoOnClickListener: (Photo) -> Unit
        ) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MainItemBinding
            .inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return photos.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
        holder.itemView.setOnClickListener{ photoOnClickListener(photos[position]) }
    }

    class ViewHolder(private val binding: MainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            Glide.with(binding.root.context).load(photo.getMedium640Url()).into(binding.photo);
        }
    }

    fun setItems(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }
}