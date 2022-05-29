package com.baig.taskgrocery.listeners

import com.baig.taskgrocery.databinding.GroceryListItemBinding
import com.baig.taskgrocery.models.Products

interface ItemClickHandler {
    fun onItemClick(product: Products, binding: GroceryListItemBinding)
}