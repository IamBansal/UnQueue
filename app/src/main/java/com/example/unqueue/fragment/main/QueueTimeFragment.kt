package com.example.unqueue.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNotify.setOnClickListener {
            notifyOnTurn()
        }

    }

    private fun notifyOnTurn() {
        Toast.makeText(requireContext(), "pending", Toast.LENGTH_SHORT).show()
    }

}