package com.baig.taskgrocery.network

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

class MyApi {

    companion object {
        fun getJsonFromAssets(context: Context, fileName: String?): String? {
            val jsonString: String = try {
                val `is` = context.assets.open(fileName!!)
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer, Charset.forName("UTF-8"))
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            return jsonString
        }
    }
}