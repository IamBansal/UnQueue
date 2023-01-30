package com.example.unqueue.fragment.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.unqueue.activity.MainActivity
import com.example.unqueue.activity.RegisterActivity
import com.example.unqueue.databinding.FragmentOTPBinding

class OTPFragment : Fragment() {

    private lateinit var binding: FragmentOTPBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOTPBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            verifyAndRegister()
        }

    }

    private fun verifyAndRegister() {
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        (activity as RegisterActivity).finish()
    }

}