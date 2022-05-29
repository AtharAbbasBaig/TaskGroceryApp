package com.baig.taskgrocery.models

import com.google.gson.annotations.SerializedName


data class GroceryData(
    @SerializedName("banners") var banners: ArrayList<Banners> = arrayListOf(),
    @SerializedName("products") var products: ArrayList<Products> = arrayListOf()
)
