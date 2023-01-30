package com.example.unqueue.fragment.main

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.unqueue.R
import com.example.unqueue.databinding.FragmentQueueBinding
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
        //TODO - fetch waiting time
        findNavController().navigate(R.id.action_QueueFragment_to_queueTimeFragment)
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