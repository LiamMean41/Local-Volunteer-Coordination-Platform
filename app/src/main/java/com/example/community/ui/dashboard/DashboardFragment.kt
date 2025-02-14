package com.example.community.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.community.Event
import com.example.community.EventAdapter
//import com.example.community.EventItemDecoration
import com.example.community.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       // binding.recyclerView.addItemDecoration(EventItemDecoration(20)) // 20dp space
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Sample events
        val testEvents = listOf(
            Event(1, "Community Cleanup", "Join us for a neighborhood cleanup."),
            Event(2, "Tech Meetup", "Discuss the latest trends in technology."),
            Event(3, "Yoga Session", "Relax and unwind with a guided yoga session."),
            Event(4, "Food Drive", "Help collect food for those in need."),
            Event(5, "Coding Workshop", "Learn how to build an Android app!")
        )

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = EventAdapter(testEvents)

        // Add spacing between events (using custom ItemDecoration)
        //binding.recyclerView.addItemDecoration(EventItSemDecoration(20)) // Adds 20dp space

        // Add divider line between items
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        Log.d("DashboardFragment", "RecyclerView setup completed")


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
