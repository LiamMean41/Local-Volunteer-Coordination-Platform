package com.example.community.ui.notifications

import com.example.community.EventViewModel
import com.example.community.EventAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.community.databinding.FragmentNotificationsBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.community.R


class NotificationsFragment : Fragment() {
    private lateinit var viewModel: EventViewModel
    private var _binding: FragmentNotificationsBinding? = null
    private lateinit var eventAdapter: EventAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewSaved)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize the adapter with an empty list
        eventAdapter = EventAdapter(emptyList(), viewModel)
        recyclerView.adapter = eventAdapter

        // Observe the LiveData from the ViewModel
        viewModel.savedEvents.observe(viewLifecycleOwner) { events ->
            // Update the adapter's list of events
            eventAdapter.submitList(events)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


