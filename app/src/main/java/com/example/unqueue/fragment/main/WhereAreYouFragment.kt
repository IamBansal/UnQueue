@file:Suppress("DEPRECATION")

package com.example.unqueue.fragment.main

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unqueue.R
import com.example.unqueue.activity.MainActivity
import com.example.unqueue.activity.RegisterActivity
import com.example.unqueue.databinding.FragmentWhereAreYouBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
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
                uploadInDB(random, "Hospital")
            }
           binding.btnBank.id -> {
               random += "BAN"
               uploadInDB(random, "Bank")
            }
           binding.btnGovOffice.id -> {
               random += "GOV"
               uploadInDB(random, "Government Office")
            }
           binding.btnFoodChain.id -> {
               random += "FOC"
               uploadInDB(random, "Food Chain")
            }
           binding.Ticket.id -> {
               random += "TIC"
               uploadInDB(random, "Ticket Counter")
            }
           binding.Airport.id -> {
               random += "AIR"
               uploadInDB(random, "Airport")
            }
           binding.btnSupermarket.id -> {
               random += "SPM"
               uploadInDB(random, "Supermarket")
            }

        }
    }

    private fun uploadInDB(qid: String, place: String) {

        val progress = ProgressDialog(requireContext())
        progress.setMessage("Generating unique QID")
        progress.show()

        val userQid = hashMapOf(
            "name" to firebaseAuth.currentUser!!.displayName,
            "qid" to qid,
            "domain" to place
        )

        Firebase.firestore.collection("data").add(userQid).addOnSuccessListener {
            progress.dismiss()
            findNavController().navigate(R.id.action_whereAreYouFragment_to_QueueFragment, bundleOf(("qid" to qid)))
        }
            .addOnFailureListener { e ->
                progress.dismiss()
                Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
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