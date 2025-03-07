package com.example.community.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.community.databinding.FragmentProfileBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    // Updated URL endpoint for user data (login/signup)
    private val endpointUrl = "https://driven-gnu-ample.ngrok-free.app/users"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Check SharedPreferences for an existing login.
        val prefs = requireContext().getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        val savedUsername = prefs.getString("username", null)
        if (savedUsername != null) {
            showUserView(savedUsername)
        } else {
            binding.layoutLogin.visibility = View.VISIBLE
            binding.layoutUser.visibility = View.GONE
        }

        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        binding.btnSignUp.setOnClickListener {
            performSignUp()
        }

        // Logout button clears the stored username and shows the login view.
        binding.btnLogout.setOnClickListener {
            logoutUser()
        }

        return root
    }

    private fun performLogin() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create JSON payload for login.
        val jsonPayload = JSONObject().apply {
            put("username", username)
            put("password", password)
            put("action", "login")
        }

        sendPostRequest(jsonPayload, username)
    }

    private fun performSignUp() {
        val username = binding.etUsername.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Create JSON payload for sign up.
        val jsonPayload = JSONObject().apply {
            put("username", username)
            put("password", password)
            put("action", "signup")
        }

        sendPostRequest(jsonPayload, username)
    }

    /**
     * Sends the POST request to the backend.
     * @param jsonPayload the JSON payload to send.
     * @param username the username used to update the UI upon success.
     */
    private fun sendPostRequest(jsonPayload: JSONObject, username: String) {
        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = jsonPayload.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url(endpointUrl)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                requireActivity().runOnUiThread {
                    if (response.isSuccessful) {
                        // Save the logged-in user's username so they stay logged in.
                        saveUserLogin(username)
                        showUserView(username)
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Error: ${response.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    /**
     * Saves the username in SharedPreferences.
     */
    private fun saveUserLogin(username: String) {
        val prefs = requireContext().getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        prefs.edit().putString("username", username).apply()
    }

    /**
     * Clears the saved username and shows the login view.
     */
    private fun logoutUser() {
        val prefs = requireContext().getSharedPreferences("UserPref", Context.MODE_PRIVATE)
        prefs.edit().remove("username").apply()
        showLoginView()
    }

    /**
     * Updates the UI to show the user view.
     */
    private fun showUserView(username: String) {
        binding.layoutLogin.visibility = View.GONE
        binding.layoutUser.visibility = View.VISIBLE
        binding.tvUserWelcome.text = "Welcome, $username!"
    }

    /**
     * Resets the UI to show the login view.
     */
    private fun showLoginView() {
        binding.etUsername.text.clear()
        binding.etPassword.text.clear()
        binding.layoutLogin.visibility = View.VISIBLE
        binding.layoutUser.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
