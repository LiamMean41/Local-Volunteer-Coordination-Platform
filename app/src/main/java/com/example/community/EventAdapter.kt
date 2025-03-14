package com.example.community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageButton
import android.widget.Toast

class EventAdapter(private var events: List<Event>, private val viewModel: EventViewModel) :
    RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.detailTitle)
        val description: TextView = itemView.findViewById(R.id.detailDescription)
        val btnSave: ImageButton = itemView.findViewById(R.id.btnSaveEvent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.title.text = event.title
        holder.description.text = event.description

        holder.btnSave.setOnClickListener {
            viewModel.insertEvent(event)
            Toast.makeText(holder.itemView.context, "Event Saved!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = events.size

    // Method to update the list of events in the adapter
    fun submitList(newEvents: List<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}
