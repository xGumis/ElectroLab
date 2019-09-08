package com.example.canvastest.views

import android.R.attr.*
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.graphics.*
import android.graphics.drawable.shapes.Shape
import android.widget.ImageView
import com.example.canvastest.elements.FlowSource
import com.example.canvastest.elements.Resistor
import com.example.canvastest.elements.TensionSource
import androidx.core.view.ViewCompat
import android.view.View
import android.widget.LinearLayout


class UpMenuItem(context: Context, val type:Type):ImageView(context) {
    companion object{
        enum class Type{
            Resistor,FlowSource, TensionSource
        }
        private val sp = Point(100, 100)
        private val ep = Point(300, 100)
        private val paint =Paint().apply {
            color = Color.BLACK
            strokeWidth = 5f
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
            style = Paint.Style.STROKE
            isAntiAlias = true
            textSize = 50F
        }
    }

    private val drawable:Shape
    init {
        drawable = when(type)
        {
            Companion.Type.Resistor -> Resistor(sp,ep )
            Companion.Type.FlowSource -> FlowSource(sp,ep)
            Companion.Type.TensionSource -> TensionSource(sp,ep)
        }
        minimumWidth =300
        minimumHeight = 150

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        //params.setMargins(10, 10, 10, 10)

        //setLayoutParams(params)

        elevation = 10F

        setOnLongClickListener(MyLongClick())
    }

    override fun onDraw(canvas: Canvas?) {
        ViewCompat.setElevation(this, 10F)
        super.onDraw(canvas)
        drawable.draw(canvas, paint)
    }
    inner class MyLongClick: View.OnLongClickListener
    {
        override fun onLongClick(v: View): Boolean {
            val item = ClipData.Item(type.toString())
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData("tag", mimeTypes, item)
            val dragshadow = View.DragShadowBuilder(v)
            v.startDrag(
                data        // data to be dragged
                , dragshadow   // drag shadow builder
                , v           // local data about the drag and drop operation
                , 0          // flags (not currently used, set to 0)
            )
            return true
        }
    }
}