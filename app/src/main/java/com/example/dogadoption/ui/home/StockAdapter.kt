package com.example.dogadoption.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogadoption.R
import com.example.dogadoption.data.remote.model.Stock
import com.example.dogadoption.databinding.ItemStockBinding

class StockAdapter(
    private val onStockClicked: (Stock) -> Unit,
    private val onStockLongClicked: (Stock) -> Unit
) : ListAdapter<Stock, StockAdapter.StockViewHolder>(StockDiffCallback()) {

    inner class StockViewHolder(
        private val binding: ItemStockBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(stock: Stock) {
            binding.textBreedName.text = stock.name.ifBlank { stock.symbol }
            binding.textSubBreedCount.text = stock.symbol
            binding.textCountBadge.visibility = View.GONE
            binding.textUserAddedBadge.visibility =
                if (stock.isLocallyAdded) View.VISIBLE else View.GONE
            Glide.with(binding.root.context)
                .load(stock.logoUrl.ifBlank { null })
                .placeholder(R.drawable.ic_placeholder_stock)
                .error(R.drawable.ic_placeholder_stock)
                .centerInside()
                .into(binding.imageBreed)
            binding.root.setOnClickListener { onStockClicked(stock) }
            binding.root.setOnLongClickListener {
                if (stock.isLocallyAdded) {
                    onStockLongClicked(stock)
                    true
                } else {
                    false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding = ItemStockBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private class StockDiffCallback : DiffUtil.ItemCallback<Stock>() {
        override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean =
            oldItem.symbol == newItem.symbol && oldItem.localId == newItem.localId

        override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean =
            oldItem == newItem
    }
}
