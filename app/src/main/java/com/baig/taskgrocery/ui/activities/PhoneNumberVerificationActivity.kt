package com.baig.taskgrocery.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.baig.taskgrocery.R
import com.baig.taskgrocery.databinding.ActivityOtpverificationBinding
import com.baig.taskgrocery.firebase.PhoneNumberVerificationHandler
import com.baig.taskgrocery.ui.activities.UserSignInActivity.Companion.PHONE_NO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class PhoneNumberVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpverificationBinding
    private lateinit var phoneNumberVerificationHandler: PhoneNumberVerificationHandler
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otpverification)
        phoneNumberVerificationHandler = PhoneNumberVerificationHandler(this)
        firebaseAuth = FirebaseAuth.getInstance()

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