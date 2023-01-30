package com.example.unqueue.fragment.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.unqueue.R
import com.example.unqueue.activity.MainActivity
import com.example.unqueue.activity.RegisterActivity
import com.example.unqueue.databinding.FragmentRegisterBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseApp.initializeApp(requireActivity())

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = FirebaseAuth.getInstance()

        binding.btnContinue.setOnClickListener {
            sendOtp()
        }

        binding.btnGoogleSignUp.setOnClickListener {
            signUpWithGoogle()
        }

    }

    private fun sendOtp() {

        val name = binding.etName.text.toString().trim()
            val phoneNumber = binding.etPhone.text.toString().trim()
            val countryCode = binding.etContact.selectedCountryCode.toString().trim()
            val dialog = (activity as RegisterActivity).setProgressDialog(requireContext(), "Requesting OTP...")

            if (phoneNumber.isNotEmpty() && name.isNotEmpty()) {
                if (phoneNumber.length == 10) {

                    dialog.show()
                    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                            Log.d(tag, "onVerificationCompleted:$credential")
                        }

                        override fun onVerificationFailed(e: FirebaseException) {
                            Log.w(tag, "onVerificationFailed", e)
                            dialog.dismiss()
                            Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onCodeSent(
                            verificationId: String,
                            token: PhoneAuthProvider.ForceResendingToken
                        ) {
                            Log.d(tag, "onCodeSent:$verificationId")
                            val bundle = bundleOf(("otp" to verificationId), ("number" to phoneNumber), ("code" to countryCode), ("name" to name))
                            dialog.dismiss()
                            findNavController().navigate(R.id.register_to_otp, bundle)
                        }
                    }

                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+$countryCode$phoneNumber")
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(requireActivity())
                        .setCallbacks(callbacks)
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)

                } else {
                    Toast.makeText(requireActivity(), "Please enter correct mobile number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireActivity(), "Please enter all fields first.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun signUpWithGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            handleResult(task)
        } else {
//            Toast.makeText(requireActivity(), "Result Not okay", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        if (completedTask.isSuccessful) {
            val account: GoogleSignInAccount? = completedTask.result
            if (account != null) {
                val dialog = (activity as RegisterActivity).setProgressDialog(requireContext(), "Signing you in...")
                dialog.show()
                updateUI(account, dialog)
            }
        } else{
            Toast.makeText(requireActivity(), completedTask.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount, dialog: AlertDialog) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                dialog.dismiss()
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                Toast.makeText(requireActivity(), "Sign in successful with\nUsername : ${account.displayName}\nEmail : ${account.email}", Toast.LENGTH_SHORT).show()
                (activity as RegisterActivity).finish()
            } else {
                Toast.makeText(requireActivity(), task.exception.toString(), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
    }


}