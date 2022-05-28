package com.baig.taskgrocery.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.baig.taskgrocery.R
import com.baig.taskgrocery.databinding.ActivityMainBinding
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpPosterHeader()
    }

    private fun setUpPosterHeader() {
        val imageList = ArrayList<SlideModel>()
        imageList.add(
            SlideModel(
                "https://bit.ly/2YoJ77H"
            )
        )
        imageList.add(
            SlideModel(
                "https://bit.ly/2BteuF2"
            )
        )
        imageList.add(SlideModel("https://bit.ly/3fLJf72"))
        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)
    }
}