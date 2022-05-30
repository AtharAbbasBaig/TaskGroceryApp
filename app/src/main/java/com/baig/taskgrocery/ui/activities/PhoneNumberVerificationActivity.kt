package com.baig.taskgrocery.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.baig.taskgrocery.R
import com.baig.taskgrocery.databinding.ActivityPhoneNumberVerificationBinding
import com.baig.taskgrocery.firebase.PhoneNumberVerificationHandler
import com.baig.taskgrocery.ui.activities.UserSignInActivity.Companion.PHONE_NO
import com.google.firebase.auth.PhoneAuthProvider

class PhoneNumberVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneNumberVerificationBinding
    private lateinit var phoneNumberVerificationHandler: PhoneNumberVerificationHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_number_verification)
        phoneNumberVerificationHandler = PhoneNumberVerificationHandler(this)

        sendUserVerificationCode()
        clickToVerify()
    }

    private fun sendUserVerificationCode() {
        val phoneNo = intent.getStringExtra(PHONE_NO) ?: return
        phoneNumberVerificationHandler.sendVerificationCode(phoneNo)
    }

    private fun clickToVerify() {
        binding.apply {
            btnVerifyPin.setOnClickListener {
                val userOTP = userOTPView.otp
                if (userOTP!!.length == 6)
                    verifyCode(userOTP)
            }
        }
    }

    private fun verifyCode(userCode: String?) {
        if (userCode != null) {
            val credentials = PhoneAuthProvider.getCredential(
                phoneNumberVerificationHandler.getVerificationId(),
                userCode
            )
            phoneNumberVerificationHandler.signInWithPhoneAuthCredential(credentials)
        }
    }
}