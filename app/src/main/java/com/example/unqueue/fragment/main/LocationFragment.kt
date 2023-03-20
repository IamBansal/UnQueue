@file:Suppress("DEPRECATION")

package com.example.unqueue.fragment.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unqueue.entity.Adapter
import com.example.unqueue.databinding.FragmentLocationBinding
import com.example.unqueue.entity.User
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestPermission()
        firebaseAuth = FirebaseAuth.getInstance()
        val domain = arguments?.getString("domain")!!
        val qid = arguments?.getString("qid")!!
        val list = arrayListOf<String?>()
        var finalCity: String

        "Select any $domain nearby".also { binding.tvText.text = it }

        binding.etCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                finalCity = s.toString()
                getList(finalCity, domain, list, qid)

            }
        })

        binding.tvCurrentLocation.setOnClickListener {
            finalCity = getCurrentLocation()
        }

    }

    private fun getCurrentLocation(): String {
        var cityName = ""
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return cityName
        }

        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val location: Location = task.result
            val myLocation = LatLng(location.latitude, location.longitude)

            val geocoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses: List<Address>? =
                geocoder.getFromLocation(myLocation.latitude, myLocation.longitude, 1)
            cityName = addresses!![0].locality

            binding.etCity.setText(cityName)

        }
        return cityName
    }

    //Function to get the institutions in that area
    private fun getList(city: String, domain: String?, list: ArrayList<String?>, qid: String) {
        FirebaseDatabase.getInstance().reference.child("admin").child(domain!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnaps in snapshot.children) {
                        val user: User? = dataSnaps.getValue(User::class.java)
                        if (user!!.location == city) {
                            list.add(user.name)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        val adapter = Adapter(requireContext(), list, qid, domain, city)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())

//        val adapter = ArrayAdapter(requireActivity(), R.layout.spinner_item, list)
//        adapter.setDropDownViewResource(R.layout.spinner_item)
//        binding.spinner.adapter = adapter
//        adapter.notifyDataSetChanged()
//
//        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                Toast.makeText(
//                    requireContext(),
//                    parent!!.getItemAtPosition(position).toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
//                uploadInRealtime(qid, domain, parent.getItemAtPosition(position).toString(), city)
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                Toast.makeText(requireContext(), "No item selected", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
                    requireActivity (),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            100
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Toast.makeText(context, "Grant permission to access location.", Toast.LENGTH_SHORT).show()
    }

}