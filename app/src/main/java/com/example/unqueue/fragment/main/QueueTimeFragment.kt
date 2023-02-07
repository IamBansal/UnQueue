package com.example.unqueue.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unqueue.R
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

        binding.tvPeopleWaiting.text = getString(R.string._people_are_waiting_ahead_in_your_queue, arguments?.getString("noOfPeople"))
        binding.tvWaitingTime.text = getString(R.string.average_waiting_time_is_10_minutes, arguments?.getString("waitingTime"))

    }

    private fun notifyOnTurn() {
        Toast.makeText(requireContext(), "Pending action", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_queueTimeFragment_to_thanksFragment)
    }

}