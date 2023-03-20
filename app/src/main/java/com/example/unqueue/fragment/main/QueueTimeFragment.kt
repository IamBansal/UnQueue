package com.example.unqueue.fragment.main

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unqueue.R
import com.example.unqueue.activity.MainActivity
import com.example.unqueue.databinding.FragmentQueueTimeBinding
import com.example.unqueue.notification.ReminderBroadcast

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
            notifyOnTurn(arguments?.getString("waitingTime"))
        }

        binding.tvPeopleWaiting.text = getString(R.string._people_are_waiting_ahead_in_your_queue, arguments?.getString("noOfPeople"))
        binding.tvWaitingTime.text = getString(R.string.average_waiting_time_is_10_minutes, arguments?.getString("waitingTime"))

    }

    private fun notifyOnTurn(waitingTime: String?) {

        Toast.makeText(requireContext(), "You will be notified 15 minutes before your turn.", Toast.LENGTH_SHORT).show()

        val intent = Intent(requireContext(), ReminderBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        val alarmManager = (activity as MainActivity).getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        val currentTime = System.currentTimeMillis()
//        val minutesAfter = (waitingTime!!.toInt() - 15) * 1000
        val secondsAfter = (waitingTime!!.toInt() - 15) * 1000 * 60
        alarmManager.set(AlarmManager.RTC_WAKEUP, currentTime + secondsAfter, pendingIntent)

        findNavController().navigate(R.id.action_queueTimeFragment_to_thanksFragment)
    }

}