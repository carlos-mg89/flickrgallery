package com.example.flickrgallery.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickrgallery.R
import com.example.flickrgallery.databinding.SavedPhotosItemBinding
import com.example.flickrgallery.model.Photo
import com.example.flickrgallery.ui.common.basicDiffUtil
import com.example.flickrgallery.ui.common.bindingInflate


class SavedPhotosAdapter(
        var onPhotoItemClicked: (Photo) -> Unit,
        var onDeleteBtnClicked: (Photo) -> Unit
) : RecyclerView.Adapter<SavedPhotosAdapter.ViewHolder>(){
    var photos: List<Photo> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.saved_photos_item, false))





    override fun getItemCount(): Int {
        return photos.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.setPhoto(photos[position])
        holder.itemView.setOnClickListener{ onPhotoItemClicked(photos[position]) }
        holder.deleteBtn.setOnClickListener{ onDeleteBtnClicked(photos[position]) }
    }

    class ViewHolder(val binding: SavedPhotosItemBinding) : RecyclerView.ViewHolder(binding.root){
        val deleteBtn = binding.deleteBtn
    }
}