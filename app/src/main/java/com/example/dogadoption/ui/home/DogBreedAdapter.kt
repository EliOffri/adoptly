package com.example.dogadoption.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogadoption.R
import com.example.dogadoption.data.remote.model.DogBreed
import com.example.dogadoption.databinding.ItemDogBreedBinding

class DogBreedAdapter(
    private val onBreedClicked: (DogBreed) -> Unit,
    private val onBreedLongClicked: (DogBreed) -> Unit
) : ListAdapter<DogBreed, DogBreedAdapter.BreedViewHolder>(DogBreedDiffCallback()) {

    inner class BreedViewHolder(
        private val binding: ItemDogBreedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dogBreed: DogBreed) {
            binding.textBreedName.text =
                dogBreed.name.replaceFirstChar { it.uppercase() }
            binding.textSubBreedCount.text = if (dogBreed.subBreeds.isEmpty()) {
                binding.root.context.getString(R.string.no_sub_breeds)
            } else {
                binding.root.context.getString(R.string.sub_breeds_count, dogBreed.subBreeds.size)
            }
            if (dogBreed.subBreeds.isNotEmpty()) {
                binding.textCountBadge.text =
                    binding.root.context.getString(R.string.sub_breeds_count, dogBreed.subBreeds.size)
                binding.textCountBadge.visibility = View.VISIBLE
            } else {
                binding.textCountBadge.visibility = View.GONE
            }
            binding.textUserAddedBadge.visibility =
                if (dogBreed.isLocallyAdded) View.VISIBLE else View.GONE
            Glide.with(binding.root.context)
                .load(dogBreed.imageUrl.ifBlank { null })
                .placeholder(R.drawable.ic_placeholder_dog)
                .error(R.drawable.ic_placeholder_dog)
                .centerCrop()
                .into(binding.imageBreed)
            binding.root.setOnClickListener { onBreedClicked(dogBreed) }
            binding.root.setOnLongClickListener {
                if (dogBreed.isLocallyAdded) {
                    onBreedLongClicked(dogBreed)
                    true
                } else {
                    false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val binding = ItemDogBreedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DogBreedDiffCallback : DiffUtil.ItemCallback<DogBreed>() {
        override fun areItemsTheSame(oldItem: DogBreed, newItem: DogBreed): Boolean =
            oldItem.name == newItem.name && oldItem.localId == newItem.localId

        override fun areContentsTheSame(oldItem: DogBreed, newItem: DogBreed): Boolean =
            oldItem == newItem
    }
}
