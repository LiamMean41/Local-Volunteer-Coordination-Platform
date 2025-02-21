package com.example.community.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.community.databinding.FragmentDashboardBinding
import org.json.JSONArray

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Call function to fetch posts
        fetchPosts()
    }

    private fun fetchPosts() {
        val url = "https://192.168.0.203:5000/posts" // Replace with your Flask server IP
        val requestQueue: RequestQueue = Volley.newRequestQueue(requireContext())
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response: JSONArray ->

                showToast(requireContext(), "Attempting to connect!")
                for (i in 0 until response.length()) {
                    val post = response.getJSONObject(i)
                    Log.d("SQLite Server", "Title: ${post.getString("title")}")
                }
                showToast(requireContext(), "✅ Connected to database successfully!")
            },
            { error ->
                Log.e("Error", "Failed to fetch posts: ${error.message}")
                showToast(requireContext(), "❌ Failed to connect to database!")
            }
        )

        requestQueue.add(jsonArrayRequest)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
