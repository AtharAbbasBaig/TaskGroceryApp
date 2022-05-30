package com.baig.taskgrocery.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baig.taskgrocery.R
import com.baig.taskgrocery.databinding.GroceryListItemBinding
import com.baig.taskgrocery.listeners.ItemClickHandler
import com.baig.taskgrocery.models.Products
import com.baig.taskgrocery.utils.loadImage
import com.baig.taskgrocery.utils.visible

class ListTwoAdapter(
    private val context: Context,
    private val itemClickHandler: ItemClickHandler
) :
    ListAdapter<Products, ListTwoAdapter.ListTwoViewHolder>(Differentiator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTwoViewHolder {
        return ListTwoViewHolder(
            GroceryListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ListTwoViewHolder, position: Int) {

        val item = getItem(position)
        item?.let { holder.bindItems(it) }
    }

    inner class ListTwoViewHolder(private val binding: GroceryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItems(product: Products) = with(binding) {
            itemImage.loadImage(product.imageUrl!!)
            if (product.discount != null) {
                tvDiscount.visible(true)
                "${product.discount}\noff".also { tvDiscount.text = it }
            } else {
                tvDiscount.visible(false)
            }
            tvItemName.text = product.label
            "PKR ${product.price} (${product.weight} kg)".also { tvItemPriceDetails.text = it }
            if (product.outOfStock == false) {
                "Add To Cart".also { itemButtonAddToCart.text = it }
                itemButtonAddToCart.setOnClickListener {
                    itemButtonAddToCart.visible(false)
                    containerCartAddRemover.visible(true)
                }
                itemClickHandler.onItemClick(product, binding.root)
            } else {
                "Out Of Stock".also { itemButtonAddToCart.text = it }
                itemButtonAddToCart.isClickable = false
                itemButtonAddToCart.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.round_btn_out_of_stock
                    )
                )
            }
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