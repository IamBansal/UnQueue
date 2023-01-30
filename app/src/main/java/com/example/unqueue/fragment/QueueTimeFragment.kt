package com.example.unqueue.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unqueue.databinding.FragmentQueueTimeBinding

class QueueTimeFragment : Fragment() {

    private lateinit var binding: FragmentQueueTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQueueTimeBinding.inflate(inflater, container, false)
        return binding.root
    }
}