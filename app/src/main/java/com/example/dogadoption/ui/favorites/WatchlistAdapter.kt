package com.example.dogadoption.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogadoption.R
import com.example.dogadoption.data.local.entity.WatchlistEntity
import com.example.dogadoption.databinding.ItemWatchlistBinding

class WatchlistAdapter : ListAdapter<WatchlistEntity, WatchlistAdapter.WatchlistViewHolder>(WatchlistDiffCallback()) {

    inner class WatchlistViewHolder(
        private val binding: ItemWatchlistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entity: WatchlistEntity) {
            binding.textFavoriteBreedName.text = entity.name.ifBlank { entity.symbol }
            binding.textFavoriteNotes.text = entity.notes.ifBlank {
                binding.root.context.getString(R.string.no_notes)
            }
            binding.textStatusBadge.text = entity.symbol
            Glide.with(binding.root.context)
                .load(entity.logoUrl.ifBlank { null })
                .placeholder(R.drawable.ic_placeholder_stock)
                .error(R.drawable.ic_placeholder_stock)
                .centerInside()
                .into(binding.imageFavoriteBreed)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val binding = ItemWatchlistBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return WatchlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class WatchlistDiffCallback : DiffUtil.ItemCallback<WatchlistEntity>() {
        override fun areItemsTheSame(oldItem: WatchlistEntity, newItem: WatchlistEntity): Boolean =
            oldItem.symbol == newItem.symbol

        override fun areContentsTheSame(oldItem: WatchlistEntity, newItem: WatchlistEntity): Boolean =
            oldItem == newItem
    }
}
