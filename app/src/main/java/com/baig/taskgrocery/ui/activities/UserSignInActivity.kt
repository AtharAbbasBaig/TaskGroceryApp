package com.baig.taskgrocery.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
                checkAllBoxesAndNavigate()
            }
        }
    }

    private fun checkAllBoxesAndNavigate() {
        if (binding.editTextUserPhoneNo.text.toString() != ""
            && binding.editTextUserName.toString() != ""
            && binding.editTextUsePassword.toString() != ""
        ) {
            val phoneNumber = binding.editTextUserPhoneNo.text.toString()
            Intent(this@UserSignInActivity, PhoneNumberVerificationActivity::class.java).apply {
                putExtra(PHONE_NO, phoneNumber)
                startActivity(this)
            }
        } else {
            Toast.makeText(this, "Please fill All Boxes!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val PHONE_NO = "phone_number"
    }
}