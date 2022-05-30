package com.baig.taskgrocery.models

data class Products(
    var id: String? = null,
    var label: String? = null,
    var description: String? = null,
    var imageUrl: String? = null,
    var outOfStock: Boolean? = null,
    var price: Int? = null,
    var weight: Int? = null,
    var discount: String? = null
)
