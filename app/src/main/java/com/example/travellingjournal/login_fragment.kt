package com.example.travellingjournal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.travellingjournal.databinding.LoginFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    // Firebase authentication instance
    private lateinit var auth: FirebaseAuth

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        _binding = LoginFragmentBinding.inflate(inflater, container, false)

        // Set up login button click listener
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(activity, "Please enter email and password.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "Login successful", Toast.LENGTH_SHORT).show()
                    // Navigate to the next activity or fragment
                } else {
                    Toast.makeText(activity, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    Log.e("LoginFragment", "Login failed", task.exception)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
