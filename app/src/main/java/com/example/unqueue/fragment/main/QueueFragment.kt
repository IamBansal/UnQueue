@file:Suppress("DEPRECATION")

package com.example.unqueue.fragment.main

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unqueue.R
import com.example.unqueue.databinding.FragmentQueueBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

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

        val qid = arguments?.getString("qid")

        binding.qrCode.setImageBitmap(getQrCodeBitmap(qid = qid!!))
        binding.tvQID.text = qid

        binding.btnContinue.setOnClickListener {
            getWaitingTime()
        }

    }

    private fun getWaitingTime() {
        val progress = ProgressDialog(requireContext())
        progress.setMessage("Adding you in the queue and calculating the waiting time..")
        progress.show()

        var count = -1

        Firebase.firestore.collection("data").get().addOnSuccessListener {
            progress.dismiss()
            for (document in it){
                if (document["domain"]!! == arguments?.getString("domain")){
                    count++
                }
            }
            findNavController().navigate(R.id.action_QueueFragment_to_queueTimeFragment, bundleOf(("noOfPeople" to count.toString()), ("waitingTime" to (count * 10).toString())))
        }
            .addOnFailureListener {
            progress.dismiss()
                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun getQrCodeBitmap(qid: String): Bitmap {
        val size = 512
        val bits = QRCodeWriter().encode(qid, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }
}