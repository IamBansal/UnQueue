package com.example.unqueue.fragment.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unqueue.R
import com.example.unqueue.activity.MainActivity
import com.example.unqueue.activity.RegisterActivity
import com.example.unqueue.databinding.FragmentWhereAreYouBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class WhereAreYouFragment : Fragment() {

    private lateinit var binding: FragmentWhereAreYouBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWhereAreYouBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.ivLogout.setOnClickListener {
            logout()
        }

        binding.btnHospital.setOnClickListener {
            onClick(it)
        }
        binding.btnBank.setOnClickListener {
            onClick(it)
        }
        binding.btnGovOffice.setOnClickListener {
            onClick(it)
        }
        binding.btnFoodChain.setOnClickListener {
            onClick(it)
        }
        binding.Ticket.setOnClickListener {
            onClick(it)
        }
        binding.Airport.setOnClickListener {
            onClick(it)
        }
        binding.btnSupermarket.setOnClickListener {
            onClick(it)
        }

    }

    private fun onClick(v: View) {
        var random = (1000..9999).random().toString()

        when (v.id) {
            binding.btnHospital.id -> {
                random += "HOS"
                findNavController().navigate(R.id.action_whereAreYouFragment_to_LocationFragment, bundleOf(("qid" to random), ("domain" to "Hospital")))
            }
           binding.btnBank.id -> {
               random += "BAN"
               findNavController().navigate(R.id.action_whereAreYouFragment_to_LocationFragment, bundleOf(("qid" to random), ("domain" to "Bank")))
            }
           binding.btnGovOffice.id -> {
               random += "GOV"
               findNavController().navigate(R.id.action_whereAreYouFragment_to_LocationFragment, bundleOf(("qid" to random), ("domain" to "Government Office")))
            }
           binding.btnFoodChain.id -> {
               random += "FOC"
               findNavController().navigate(R.id.action_whereAreYouFragment_to_LocationFragment, bundleOf(("qid" to random), ("domain" to "Food Chain")))
            }
           binding.Ticket.id -> {
               random += "TIC"
               findNavController().navigate(R.id.action_whereAreYouFragment_to_LocationFragment, bundleOf(("qid" to random), ("domain" to "Ticket Counter")))
            }
           binding.Airport.id -> {
               random += "AIR"
               findNavController().navigate(R.id.action_whereAreYouFragment_to_LocationFragment, bundleOf(("qid" to random), ("domain" to "Airport")))
            }
           binding.btnSupermarket.id -> {
               random += "SPM"
               findNavController().navigate(R.id.action_whereAreYouFragment_to_LocationFragment, bundleOf(("qid" to random), ("domain" to "Supermarket")))
            }

        }
    }

    private fun logout() {

        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Logout Requested!!")
            .setMessage("You sure you want to logout?")
            .setPositiveButton("Yes, logout"){_,_->
                Firebase.auth.signOut()
                startActivity(Intent(requireActivity(), RegisterActivity::class.java))
                (activity as MainActivity).finish()
            }
            .setNegativeButton("No"){_,_->}
            .create()
            .show()

    }

}