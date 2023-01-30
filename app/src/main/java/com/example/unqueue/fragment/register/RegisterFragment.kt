package com.example.unqueue.fragment.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unqueue.R
import com.example.unqueue.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            sendOtp()
        }

        binding.btnGoogleSignUp.setOnClickListener {
            signUpWithGoogle()
        }

    }

    private fun signUpWithGoogle() {
        Toast.makeText(requireContext(), "pending", Toast.LENGTH_SHORT).show()
    }

    private fun sendOtp() {
        findNavController().navigate(R.id.register_to_otp)
    }

}