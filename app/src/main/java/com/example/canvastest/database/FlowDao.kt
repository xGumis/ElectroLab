package com.example.canvastest.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FlowDao {

    @Query("SELECT * FROM _flow WHERE schemaId = :schemaId")
    fun getFlowBySchema(schemaId:Long): List<FlowEntity>
    @Insert
    fun insert(flowEntity: FlowEntity)
    @Delete
    fun delete(toDelete:List<FlowEntity>)

}