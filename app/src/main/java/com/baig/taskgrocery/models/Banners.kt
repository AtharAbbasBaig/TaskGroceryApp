package com.baig.taskgrocery.models

import com.google.gson.annotations.SerializedName


data class Banners(
    @SerializedName("id") var id: String? = null,
    @SerializedName("imageUrl") var imageUrl: String? = null
)
