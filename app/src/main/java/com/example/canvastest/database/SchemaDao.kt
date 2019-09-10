package com.example.canvastest.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SchemaDao {
    @Query("SELECT * FROM _schema")
    fun getSchemas(): LiveData<List<Schema>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(schema: Schema):Long


}