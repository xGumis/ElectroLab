package com.example.canvastest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ResistorDao {
    @Query("SELECT * FROM _reistor WHERE schemaId = :schemaId")
    fun getResistorBySchema(schemaId:Long): List<ResistorEntity>

    @Insert
    fun insert(resistor:ResistorEntity)

    @Delete
    fun delete(toDelete:List<ResistorEntity>)
}