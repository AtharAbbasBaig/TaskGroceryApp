package com.baig.taskgrocery.models

import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("id") var id: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("imageUrl") var imageUrl: String? = null,
    @SerializedName("outOfStock") var outOfStock: Boolean? = null,
    @SerializedName("price") var price: Int? = null,
    @SerializedName("weight") var weight: Int? = null,
    @SerializedName("discount") var discount: String? = null
)
