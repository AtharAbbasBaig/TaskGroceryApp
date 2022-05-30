package com.baig.taskgrocery.models


data class GroceryData(
    var banners: ArrayList<Banners> = arrayListOf(),
    var products: ArrayList<Products> = arrayListOf()
)
