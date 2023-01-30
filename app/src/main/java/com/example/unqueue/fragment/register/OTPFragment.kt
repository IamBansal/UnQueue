@file:Suppress("DEPRECATION")

package com.example.unqueue.fragment.register

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.unqueue.activity.MainActivity
import com.example.unqueue.databinding.FragmentOTPBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class OTPFragment : Fragment() {

    private lateinit var binding: FragmentOTPBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOTPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener {
            verifyAndRegister()
        }

    }

    private fun verifyAndRegister() {

        val userOTP = binding.etOTP.text.toString().trim()
        val backendOTP = arguments?.getString("otp")

        val progress = ProgressDialog(requireContext())
        progress.setMessage("Signing you in...")
        progress.show()

        if (TextUtils.isEmpty(userOTP)) {
            Toast.makeText(requireActivity(), "Please enter OTP first.", Toast.LENGTH_SHORT).show()
        } else {
            progress.show()
            val phoneAuthCredential = backendOTP?.let { PhoneAuthProvider.getCredential(it, userOTP) }
            if (phoneAuthCredential != null) {
                auth.signInWithCredential(phoneAuthCredential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("check", "FirebaseAuth credentials verified")

//                            val user = auth.currentUser
//                            val profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(name).build()
//                            user!!.updateProfile(profileUpdates)
//                            incorrectOTP.visibility = View.GONE
                            progress.dismiss()
                            startActivity(Intent(requireActivity(), MainActivity::class.java))
                            requireActivity().finish()

                        } else {
                            Log.d("check", "Enter correct OTP")
                            progress.dismiss()
                            Toast.makeText(requireActivity(), "Enter correct OTP", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }

}