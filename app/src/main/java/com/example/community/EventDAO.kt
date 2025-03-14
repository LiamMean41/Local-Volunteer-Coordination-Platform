package com.example.community

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventDAO {

    // Insert event, replacing if it already exists (to prevent duplicates)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    // Fetch all saved events (LiveData ensures UI updates automatically)
    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<Event>>

    // Delete an event
    @Delete
    suspend fun deleteEvent(event: Event)
}
