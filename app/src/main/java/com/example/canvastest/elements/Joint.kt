package com.example.canvastest.elements

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.drawable.shapes.Shape
import com.example.canvastest.model.Node2

class Joint:Shape() {

    companion object
    {
        var joints= mutableListOf<Joint>()
    }

    init {
        joints.add(this)
    }
    var elementsJoined : MutableList<Pair<Element,Boolean>> = mutableListOf()

    var node: Node2? = null

    var paint = Paint().also {
        it.color = Color.BLACK
        it.textSize = 40F
    }
    override fun draw(canvas: Canvas?, paint: Paint?) {
        lateinit var point: Point
        if(elementsJoined[0].second)
        {
           point = elementsJoined[0].first.startPoint
        }
        else
            point = elementsJoined[0].first.endPoint
        if (node != null) {
            canvas?.drawText("%.2f".format(node!!.node!!.potential), point.x.toFloat(),point.y.toFloat(),paint)
        }

    }
}