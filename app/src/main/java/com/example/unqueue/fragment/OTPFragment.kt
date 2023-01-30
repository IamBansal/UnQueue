package com.example.unqueue.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
}