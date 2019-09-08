package com.example.canvastest.database

import android.graphics.Point
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "_tension")
class TensionEntity (
    var sp: Point? = null,
    var ep: Point? = null,
    var tension:Double? = null,
    var schemaId:Long? = null,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id :Int? = null
) {
}