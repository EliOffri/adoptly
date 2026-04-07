package com.example.dogadoption.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogadoption.R
import com.example.dogadoption.data.local.entity.FavoriteDogEntity
import com.example.dogadoption.databinding.ItemFavoriteDogBinding

class FavoriteDogAdapter : ListAdapter<FavoriteDogEntity, FavoriteDogAdapter.FavoriteViewHolder>(FavoriteDiffCallback()) {

    inner class FavoriteViewHolder(
        private val binding: ItemFavoriteDogBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: FavoriteDogEntity) {
            binding.textFavoriteBreedName.text =
                entity.breedName.replaceFirstChar { it.uppercase() }
            binding.textFavoriteNotes.text = entity.notes.ifBlank {
                binding.root.context.getString(R.string.no_notes)
            }
            Glide.with(binding.root.context)
                .load(entity.imageUrl)
                .placeholder(R.drawable.ic_placeholder_dog)
                .error(R.drawable.ic_placeholder_dog)
                .centerCrop()
                .into(binding.imageFavoriteBreed)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteDogBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class FavoriteDiffCallback : DiffUtil.ItemCallback<FavoriteDogEntity>() {
        override fun areItemsTheSame(oldItem: FavoriteDogEntity, newItem: FavoriteDogEntity): Boolean =
            oldItem.breedName == newItem.breedName

        override fun areContentsTheSame(oldItem: FavoriteDogEntity, newItem: FavoriteDogEntity): Boolean =
            oldItem == newItem
    }
}
