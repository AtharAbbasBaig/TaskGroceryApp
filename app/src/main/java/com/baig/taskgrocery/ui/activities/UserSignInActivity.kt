package com.baig.taskgrocery.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.baig.taskgrocery.R
import com.baig.taskgrocery.databinding.ActivityUserSignInBinding
import com.baig.taskgrocery.firebase.PhoneNoVerification

class UserSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_sign_in)

        navigateToOTPActivity()
    }

    private fun navigateToOTPActivity() {
        binding.apply {
            btnSignIn.setOnClickListener {
                val phoneNumber = binding.editTextUserPhoneNo.text.toString()
                Intent(this@UserSignInActivity, OTPVerificationActivity::class.java).apply {
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