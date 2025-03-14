package com.example.community

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val eventDao = EventDatabase.getInstance(application).eventDAO()
    val savedEvents: LiveData<List<Event>> = eventDao.getAllEvents()

    fun insertEvent(event: Event) = viewModelScope.launch {
        eventDao.insertEvent(event)
    }

    fun removeEvent(event: Event) = viewModelScope.launch {
        eventDao.deleteEvent(event)
    }
}