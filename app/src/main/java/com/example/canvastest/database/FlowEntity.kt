package com.example.canvastest.database

import android.graphics.Point
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "_flow")
class FlowEntity
    (
    var sp: Point? = null,
    var ep: Point? = null,
    var flow:Double? = null,
    var schemaId:Long? = null,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id :Int? = null
) {
}