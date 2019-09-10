package com.example.canvastest.database


import android.graphics.Point
import androidx.room.TypeConverter


class PointConverter {

    @TypeConverter
    fun pointToIntArray(point:Point?):String
    {
        return "${point!!.x},${point.y}"
    }
    @TypeConverter
    fun intArrayToPoiint(str:String):Point?
    {
        val arr = str.split(',')
        return (Point(arr[0].toInt(),arr[1].toInt()))
    }
}