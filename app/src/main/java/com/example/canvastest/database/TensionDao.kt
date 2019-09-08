package com.example.canvastest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TensionDao {
    @Query("SELECT * FROM _tension WHERE schemaId = :schemaId")
    fun getTensionBySchema(schemaId:Long): List<TensionEntity>

    @Insert
    fun insert(entity:TensionEntity)
}