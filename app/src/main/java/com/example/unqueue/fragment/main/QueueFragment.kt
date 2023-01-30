package com.example.unqueue.fragment.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unqueue.R
import com.example.unqueue.databinding.FragmentQueueBinding

class QueueFragment : Fragment() {

    private lateinit var binding: FragmentQueueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQueueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener {
            getWaitingTime()
        }

    }

    private fun getWaitingTime() {
        findNavController().navigate(R.id.action_QueueFragment_to_queueTimeFragment)
    }

}