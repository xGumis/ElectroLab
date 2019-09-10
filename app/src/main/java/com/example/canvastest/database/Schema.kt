package com.example.canvastest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "_schema")
class Schema (
    var name:String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id :Long? = null)
{}