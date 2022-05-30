package com.baig.taskgrocery.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.baig.taskgrocery.R
import com.baig.taskgrocery.databinding.ActivityUserSignInBinding
import com.baig.taskgrocery.utils.PrefUtil

class UserSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSignInBinding
    private lateinit var prefUtil: PrefUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_sign_in)
        prefUtil = PrefUtil(this)

        navigateToPhoneNumberActivity()
        getPreferenceData()
    }

    private fun navigateToPhoneNumberActivity() {
        binding.apply {
            btnSignIn.setOnClickListener {
                checkAllBoxesAndNavigate()
            }
        }
    }

    private fun getPreferenceData() {
        binding.editTextUserPhoneNo.setText(prefUtil.getString("user_number"))
        binding.editTextUserName.setText(prefUtil.getString("user_name"))
        binding.editTextUsePassword.setText(prefUtil.getString("user_password"))
    }

    private fun checkAllBoxesAndNavigate() {
        val userPhoneNumber = binding.editTextUserPhoneNo.text.toString()
        val userName = binding.editTextUserName.text.toString()
        val userPassword = binding.editTextUsePassword.text.toString()
        prefUtil.setString("user_name", userName)
        prefUtil.setString("user_number", userPhoneNumber)
        prefUtil.setString("user_password", userPassword)
        if (userName.isEmpty()
            || userPhoneNumber.isEmpty()
            || userPassword.isEmpty()
        ) {
            showToast("Please fill All Boxes!")

        } else if (binding.editTextUserPhoneNo.length() < 10
        ) {
            showToast("Invalid Number!")
        } else {
            Intent(this@UserSignInActivity, PhoneNumberVerificationActivity::class.java).apply {
                putExtra(PHONE_NO, userPhoneNumber)
                startActivity(this)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val PHONE_NO = "phone_number"
    }
}