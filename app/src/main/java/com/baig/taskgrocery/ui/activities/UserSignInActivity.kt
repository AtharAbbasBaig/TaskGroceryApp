package com.baig.taskgrocery.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.baig.taskgrocery.R
import com.baig.taskgrocery.databinding.ActivityUserSignInBinding

class UserSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_sign_in)

        navigateToPhoneNumberActivity()
    }

    private fun navigateToPhoneNumberActivity() {
        binding.apply {
            btnSignIn.setOnClickListener {
                val phoneNumber = binding.editTextUserPhoneNo.text.toString()
                Intent(this@UserSignInActivity, PhoneNumberVerificationActivity::class.java).apply {
                    putExtra(PHONE_NO, phoneNumber)
                    startActivity(this)
                }
            }
        }
    }

    companion object {
        const val PHONE_NO = "phone_number"
    }
}