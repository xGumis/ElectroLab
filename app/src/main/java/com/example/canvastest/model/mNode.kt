package com.example.canvastest.model

import com.example.canvastest.elements.Synapse

class mNode {
    companion object {
        var nodeList = mutableListOf<mNode>()

        fun updatePotential() {
            //val loneNodes = mergeNodes()
            var matrix = Array(nodeList.size - 1, { i -> DoubleArray(nodeList.size - 1) })
            var equal = DoubleArray(nodeList.size - 1)

            var rowID = 0
            var columnId: Int
            nodeList.forEach { row ->
                columnId = 0
                if (row != groundedNode) {
                    nodeList.forEach { column ->
                        if (column != groundedNode) {
                            //dodawanie na przekątnych
                            if (column == row) {
                                row.synapses.forEach {
                                    if (it.flowSource == null)
                                        matrix[rowID][columnId] += 1 / it.resistance
                                }
                            } else {
                                //odejmowanie połączonych
                                row.synapses.forEach {
                                    if (it.flowSource == null && (it.from == row && it.to == column) || (it.from == column && it.to == row)) {

                                        matrix[rowID][columnId] -= 1 / it.resistance
                                    }
                                }
                            }
                            columnId++
                        }

                    }
                    //
                    row.synapses.forEach {
                        // jesli płynie od to -
                        if (it.from == row) {
                            equal[rowID] -= it.flow
                        }
                        // jesli płydie do to +
                        else
                            equal[rowID] += it.flow
                    }
                    rowID++
                }
            }



           var i =0
            val result = mSolver.lsolve(matrix, equal)
            nodeList.forEach{ it ->
                if (groundedNode != it) {
                    it.potential = result[i]
                    i++
                }

            }

        }
        var groundedNode: mNode? = null
    }

    init {
        nodeList.add(this)
        if (groundedNode == null)
            groundedNode = this
    }

    var synapses: MutableList<mSynapse> = mutableListOf()

    var potential: Double = 0.0
    fun destroy() {
        nodeList.remove(this)
        if (groundedNode == this) {
            if (nodeList.size > 0)
                groundedNode = nodeList.first()
            else {
                groundedNode = null
            }
        }
    }
}