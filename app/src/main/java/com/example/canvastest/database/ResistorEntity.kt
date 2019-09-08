package com.example.canvastest.database

import android.graphics.Point
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "_reistor")
class ResistorEntity(
    var sp:Point? = null,
    var ep:Point? = null,
    var resistance:Double? = null,
    var schemaId:Long? = null,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id :Int? = null
) {
}