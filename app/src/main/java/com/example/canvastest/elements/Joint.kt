package com.example.canvastest.elements

import com.example.canvastest.Model.Node2

class Joint {

    companion object
    {
        var joints= mutableListOf<Joint>()
    }

    init {
        joints.add(this)
    }
    var elementsJoined : MutableList<Pair<Element,Boolean>> = mutableListOf()

    var node: Node2? = null

}