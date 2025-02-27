package com.example.community.ui.dashboard

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.community.R

class PostDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        // Retrieve event details from the Intent
        val title = intent.getStringExtra("title") ?: "N/A"
        val description = intent.getStringExtra("description") ?: "N/A"
        val date = intent.getStringExtra("date") ?: "N/A"
        val time = intent.getStringExtra("time") ?: "N/A"
        val location = intent.getStringExtra("location") ?: "N/A"

        // Bind the views and display the details
        findViewById<TextView>(R.id.detailTitle).text = title
        findViewById<TextView>(R.id.detailDescription).text = description
        findViewById<TextView>(R.id.detailDate).text = "Date: $date"
        findViewById<TextView>(R.id.detailTime).text = "Time: $time"
        findViewById<TextView>(R.id.detailLocation).text = "Location: $location"
    }
}
