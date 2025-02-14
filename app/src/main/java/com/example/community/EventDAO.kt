package com.example.community

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDAO {
    @Insert
    suspend fun insertEvent(event: Event)

    @Query("SELECT * FROM events")
    fun getAllEvents(): List<Event>
}