package com.example.flickrgallery.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickrgallery.databinding.SavedPhotosItemBinding
import com.example.flickrgallery.model.Photo

class SavedPhotosAdapter(
        private var photos: List<Photo>,
        var onPhotoItemClicked: (Photo) -> Unit,
        var onDeleteBtnClicked: (Photo) -> Unit
) : RecyclerView.Adapter<SavedPhotosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SavedPhotosItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return photos.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
        holder.itemView.setOnClickListener{ onPhotoItemClicked(photos[position]) }
        holder.deleteBtn.setOnClickListener{ onDeleteBtnClicked(photos[position]) }
    }

    class ViewHolder(private val binding: SavedPhotosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val deleteBtn = binding.deleteBtn

        fun bind(photo: Photo) {
            Glide.with(binding.root.context).load(photo.getMedium640Url()).into(binding.photo)
            binding.savedDate.text = photo.savedDate.toString()
            binding.description.text = photo.title
        }
    }

    fun setItems(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }
}