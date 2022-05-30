package com.baig.taskgrocery.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.baig.taskgrocery.R
import com.baig.taskgrocery.adapters.ProductsListOneAdapter
import com.baig.taskgrocery.adapters.ProductsListTwoAdapter
import com.baig.taskgrocery.databinding.ActivityMainBinding
import com.baig.taskgrocery.listeners.ItemClickHandler
import com.baig.taskgrocery.models.Banners
import com.baig.taskgrocery.models.GroceryData
import com.baig.taskgrocery.models.Products
import com.baig.taskgrocery.network.MyApi
import com.baig.taskgrocery.utils.PrefUtil
import com.baig.taskgrocery.utils.visible
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.grocery_list_item.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), ItemClickHandler {

    private lateinit var binding: ActivityMainBinding
    private var bannerList: List<Banners> = ArrayList()
    private var productList: List<Products> = ArrayList()
    private var cartItemCounter: Int = 0
    private lateinit var prefUtil: PrefUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        prefUtil = PrefUtil(this)

        loadWithCoroutines()
    }

    private fun loadWithCoroutines() {
        lifecycleScope.launch(Dispatchers.IO) {
            //async launch
            whenStarted {
                val deferred1 = async {
                    getBannerList()
                }
                val deferred2 = async {
                    getProductList()
                }
                bannerList = deferred1.await()
                productList = deferred2.await()
                withContext(Dispatchers.Main) {
                    setUpPosterHeader()
                    setUpRecyclerViewOne()
                    setUpRecyclerViewTwo()
                    getCartCount()
                }
            }
        }
    }

    private fun getCartCount() {
        cartItemCounter = prefUtil.getInt(CART_COUNT, 0)
        if (cartItemCounter > 0)
            binding.tvAddToCartCount.visible(true)
        binding.tvAddToCartCount.text = cartItemCounter.toString()
    }

    private fun setUpRecyclerViewOne() {
        binding.apply {
            val adapter = ProductsListOneAdapter(this@MainActivity,this@MainActivity)
            groceryListRecyclerView.adapter = adapter
            adapter.submitList(productList)
        }
    }

    private fun setUpRecyclerViewTwo() {
        binding.apply {
            val adapter = ProductsListTwoAdapter(
                this@MainActivity,
                this@MainActivity
            )
            groceryList2RecyclerView.adapter = adapter
            adapter.submitList(productList)
        }
    }

    private fun setUpPosterHeader() {
        val imageList = ArrayList<SlideModel>()
        for (i in bannerList.indices) {
            imageList.add(SlideModel(bannerList[i].imageUrl))
        }
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }

    private fun getBannerList(): ArrayList<Banners> {
        val bannerList = ArrayList<Banners>()
        val jsonFileString: String? =
            MyApi.getJsonFromAssets(applicationContext, "Home Screen Data.json")
        val gson = Gson()
        val groceryData = gson.fromJson(jsonFileString, GroceryData::class.java)
        val banners = groceryData.banners
        for (i in banners.indices) {
            bannerList.add(banners[i])
        }
        return bannerList
    }

    private fun getProductList(): ArrayList<Products> {
        val productList = ArrayList<Products>()
        val jsonFileString: String? =
            MyApi.getJsonFromAssets(applicationContext, "Home Screen Data.json")
        val gson = Gson()
        val groceryData = gson.fromJson(jsonFileString, GroceryData::class.java)
        val products = groceryData.products
        for (i in products.indices) {
            productList.add(products[i])
        }
        return productList
    }

    private fun setCartItemCount() {
        if (cartItemCounter > 0) {
            binding.tvAddToCartCount.visible(true)
            binding.tvAddToCartCount.text = cartItemCounter.toString()
        } else {
            binding.tvAddToCartCount.visible(false)
        }
        prefUtil.setInt(CART_COUNT, cartItemCounter)
    }

    override fun onItemClick(product: Products, root: View) {
        val maxLimit = 5
        var initialCount = prefUtil.getInt(product.id.toString(), 0)
        root.tvItemCartCount.text = initialCount.toString()
        root.btnAddToCartPlus.setOnClickListener {
            if (initialCount < maxLimit) {
                root.btnAddToCartMinus.isClickable = true
                ++cartItemCounter
                ++initialCount
                prefUtil.setInt(product.id.toString(), initialCount)
                setCartItemCount()
                root.tvItemCartCount.text = initialCount.toString()
            } else {
                showToast("Maximum Limit Reached")
                root.btnAddToCartPlus.isClickable = false
                root.btnAddToCartMinus.isClickable = true
            }
        }
        root.btnAddToCartMinus.setOnClickListener {
            if (initialCount > 0) {
                root.btnAddToCartPlus.isClickable = true
                --cartItemCounter
                --initialCount
                prefUtil.setInt(product.id.toString(), initialCount)
                setCartItemCount()
                root.tvItemCartCount.text = initialCount.toString()
            } else {
                showToast("Minimum Limit Reached")
                root.btnAddToCartPlus.isClickable = true
                root.btnAddToCartMinus.isClickable = false
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val CART_COUNT = "cart_count"
    }
}