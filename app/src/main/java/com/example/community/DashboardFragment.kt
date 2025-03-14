package com.example.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var eventList: LiveData<List<Event>>
    private lateinit var viewModel: EventViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // loads events from the database
        val database = EventDatabase.getInstance(requireContext())
        eventList = database.eventDAO().getAllEvents()

        // Initialize the adapter with an empty list
        eventAdapter = EventAdapter(emptyList(), viewModel)
        recyclerView.adapter = eventAdapter

        // Observe the LiveData
        eventList.observe(viewLifecycleOwner) { events ->
            // Update the adapter with new event data
            eventAdapter.submitList(events)
        }
    }
}

