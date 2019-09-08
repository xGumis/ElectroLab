package com.example.canvastest.database


import android.graphics.Point
import androidx.room.TypeConverter


class PointConverter {

    @TypeConverter
    fun pointToIntArray(point:Point?):String
    {
        return ""
    }
    @TypeConverter
    fun intArrayToPoiint(str:String):Point?
    {
        return Point(0,0)
    }
}