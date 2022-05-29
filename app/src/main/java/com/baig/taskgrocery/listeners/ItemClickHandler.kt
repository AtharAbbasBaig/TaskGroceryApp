package com.baig.taskgrocery.listeners

import android.view.View
import com.baig.taskgrocery.models.Products

interface ItemClickHandler {
    fun onItemClick(product: Products, root: View)
}