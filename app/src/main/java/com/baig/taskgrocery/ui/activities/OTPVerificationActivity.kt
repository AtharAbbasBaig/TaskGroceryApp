package com.baig.taskgrocery.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.baig.taskgrocery.R
import com.baig.taskgrocery.databinding.ActivityOtpverificationBinding
import com.baig.taskgrocery.firebase.PhoneNoVerification
import com.baig.taskgrocery.ui.activities.UserSignInActivity.Companion.PHONE_NO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class OTPVerificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpverificationBinding
    private lateinit var phoneNoVerification: PhoneNoVerification
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otpverification)
        phoneNoVerification = PhoneNoVerification(this, this)
        firebaseAuth = FirebaseAuth.getInstance()

        sendUserVerificationCode()
        clickToVerify()
    }

    private fun sendUserVerificationCode() {
        val phoneNo = intent.getStringExtra(PHONE_NO) ?: return
        phoneNoVerification.sendVerificationCode(phoneNo)
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
                phoneNoVerification.getVerificationId(),
                userCode
            )
            phoneNoVerification.signInWithPhoneAuthCredential(credentials)
        }
    }
}