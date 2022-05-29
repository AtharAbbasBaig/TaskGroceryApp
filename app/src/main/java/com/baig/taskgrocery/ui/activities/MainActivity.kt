package com.baig.taskgrocery.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.baig.taskgrocery.R
import com.baig.taskgrocery.adapters.ListOneAdapter
import com.baig.taskgrocery.adapters.ListTwoAdapter
import com.baig.taskgrocery.databinding.ActivityMainBinding
import com.baig.taskgrocery.databinding.GroceryListItemBinding
import com.baig.taskgrocery.listeners.ItemClickHandler
import com.baig.taskgrocery.models.Banners
import com.baig.taskgrocery.models.GroceryData
import com.baig.taskgrocery.models.Products
import com.baig.taskgrocery.network.MyApi
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), ItemClickHandler {

    private lateinit var binding: ActivityMainBinding
    private var bannerList: List<Banners> = ArrayList()
    private var productList: List<Products> = ArrayList()
    private var cartItemCounter: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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
                }
            }
        }
    }

    private fun setUpRecyclerViewOne() {
        binding.apply {
            val adapter = ListOneAdapter()
            groceryListRecyclerView.adapter = adapter
            adapter.submitList(productList)
        }
    }

    private fun setUpRecyclerViewTwo() {
        binding.apply {
            val adapter = ListTwoAdapter(
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

    override fun onItemClick(product: Products, binding: GroceryListItemBinding) {

    }
}