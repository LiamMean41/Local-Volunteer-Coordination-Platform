package com.example.community.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.community.R
import com.example.community.databinding.FragmentDashboardBinding
import org.json.JSONArray

// Data model for a Post
data class Post(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val time: String,
    val location: String
)

// RecyclerView Adapter for Posts
class PostAdapter(private var posts: List<Post>) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    // Lambda function for handling item clicks
    var onItemClick: ((Post) -> Unit)? = null

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Make sure recycler_view_item_1.xml has a TextView with the ID "postTitle"
        val titleTextView: TextView = itemView.findViewById(R.id.postTitle)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(posts[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item_1, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.titleTextView.text = post.title
    }

    override fun getItemCount() = posts.size

    fun updateData(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }
}

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // Declare the adapter
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the adapter with an empty list and set up the RecyclerView.
        // The RecyclerView ID in your fragments_dashboard.xml is "recyclerView"
        postAdapter = PostAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = postAdapter

        // Set the click listener to navigate to the details view
        postAdapter.onItemClick = { post ->
            navigateToPostDetail(post)
        }

        // Fetch posts from the server
        fetchPosts()
    }

    private fun fetchPosts() {
        val url = "https://driven-gnu-ample.ngrok-free.app/posts"
        val requestQueue: RequestQueue = Volley.newRequestQueue(requireContext())
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response: JSONArray ->
                showToast(requireContext(), "Attempting to connect!")
                val postsList = mutableListOf<Post>()
                for (i in 0 until response.length()) {
                    val postJson = response.getJSONObject(i)
                    val post = Post(
                        id = postJson.getInt("id"),
                        title = postJson.getString("title"),
                        description = postJson.getString("description"),
                        date = postJson.getString("date"),
                        time = postJson.getString("time"),
                        location = postJson.getString("location")
                    )
                    postsList.add(post)
                    Log.d("SQLite Server", "Title: ${post.title}")
                }
                // Update adapter with the fetched posts
                postAdapter.updateData(postsList)
                showToast(requireContext(), "✅ Connected to database successfully!")
            },
            { error ->
                Log.e("Error", "Failed to fetch posts: ${error.message}")
                showToast(requireContext(), "❌ Failed to connect to database!")
            }
        )
        requestQueue.add(jsonArrayRequest)
    }

    // Navigate to the detail view (new Activity) for the given post
    private fun navigateToPostDetail(post: Post) {
        val intent = Intent(requireContext(), PostDetailActivity::class.java)
        intent.putExtra("title", post.title)
        intent.putExtra("description", post.description)
        intent.putExtra("date", post.date)
        intent.putExtra("time", post.time)
        intent.putExtra("location", post.location)
        startActivity(intent)
    }

    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
