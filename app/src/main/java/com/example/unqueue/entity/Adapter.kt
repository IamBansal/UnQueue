@file:Suppress("DEPRECATION")

package com.example.unqueue.entity

import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.unqueue.R
import com.example.unqueue.databinding.SpinnerItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.ArrayList
import java.util.HashMap

class Adapter(private var context: Context, private var list: ArrayList<String?>, private var qid: String,private var domain: String, private var location: String) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(binding: SpinnerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val text = binding.text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SpinnerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = list[position]
        holder.text.setOnClickListener {

            val progress = ProgressDialog(context)
            progress.setMessage("Generating unique QID")
            progress.show()

            val firebaseAuth = FirebaseAuth.getInstance()

            val map = HashMap<String, Any>()
            map["name"] = firebaseAuth.currentUser!!.displayName.toString()
            map["qid"] = qid
            map["domain"] = domain
            map["company"] = list[position].toString()
            map["location"] = location

            FirebaseDatabase.getInstance().reference.child("users")
                .child(firebaseAuth.currentUser!!.uid).updateChildren(
                    map
                ).addOnCompleteListener { task ->
                    progress.dismiss()
                    if (task.isSuccessful) {
                        it.findNavController()
                            .navigate(R.id.locationToQueue, bundleOf(("qid" to qid), ("domain" to domain)))
                    } else {
                        Toast.makeText(context, task.exception!!.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}