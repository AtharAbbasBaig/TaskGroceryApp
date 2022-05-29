package com.baig.taskgrocery.firebase

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.baig.taskgrocery.ui.activities.MainActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class PhoneNoVerification(private val activity: Activity, private val context: Context) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private var mVerificationId: String = ""
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            val code = credential.smsCode
            if (code != null)
                signInWithPhoneAuthCredential(credential)
            else
                context
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
            } else
                if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId
            mResendToken = token
        }
    }

    fun getVerificationId(): String {
        return mVerificationId
    }

    fun sendVerificationCode(phoneNo: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber("+92$phoneNo")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = task.result?.user
                    Intent(context, MainActivity::class.java).apply {
                        context.startActivity(this)
                    }

                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(context, task.exception.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                    Toast.makeText(context, "Authentication Failed!", Toast.LENGTH_SHORT).show()
                }
            }
    }

}