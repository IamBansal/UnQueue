package com.example.unqueue.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

}