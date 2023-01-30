package com.example.unqueue.fragment.main

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unqueue.R
import com.example.unqueue.databinding.FragmentWhereAreYouBinding

class WhereAreYouFragment : Fragment() {

    private lateinit var binding: FragmentWhereAreYouBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWhereAreYouBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            generateQID()
        }

        binding.ivLogout.setOnClickListener {
            logout()
        }

    }

    private fun logout() {

        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Logout Requested!!")
            .setMessage("You sure you want to logout?")
            .setPositiveButton("Yes, logout"){_,_->

            }
            .setNegativeButton("No"){_,_->}
            .create()
            .show()

    }

    private fun generateQID() {
        findNavController().navigate(R.id.action_whereAreYouFragment_to_QueueFragment)
    }

}