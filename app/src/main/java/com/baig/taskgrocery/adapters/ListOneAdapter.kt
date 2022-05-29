package com.baig.taskgrocery.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baig.taskgrocery.databinding.GroceryListItemBinding
import com.baig.taskgrocery.models.Products
import com.baig.taskgrocery.utils.loadImage

class ListOneAdapter() :
    ListAdapter<Products, ListOneAdapter.ListOneViewHolder>(Differentiator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListOneViewHolder {
        return ListOneViewHolder(
            GroceryListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ListOneViewHolder, position: Int) {

        val item = getItem(position)
        item?.let { holder.bindWallpapers(it, position) }
    }

    inner class ListOneViewHolder(private val binding: GroceryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindWallpapers(product: Products, position: Int) = with(binding) {
            itemImage.loadImage(product.imageUrl!!)
            tvItemName.text = product.label
            "PKR ${product.price} (${product.weight} kg)".also { tvItemPriceDetails.text = it }
        }
    }

    object Differentiator : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }
    }
}