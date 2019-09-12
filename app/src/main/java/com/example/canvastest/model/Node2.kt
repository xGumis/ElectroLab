package com.example.canvastest.Model

import android.util.Log
import com.example.canvastest.elements.*
import com.example.canvastest.model.*

class Node2 {

    companion object {
        fun createMatrix(){
            var nodes2 = mutableListOf<Node2>()
            Joint.joints.forEach{
                if(it.elementsJoined.size>2)
                {
                    val node = Node2()
                    node.join = it
                    node.node = mNode()
                    it.node = node
                    nodes2.add(node)
                }
            }


            var synapseElemet=ArrayList<Element>(Element.elements)
            var path = Array<Boolean>(synapseElemet.size,{i -> false })
            nodes2.forEach{ node->
                node.join?.elementsJoined?.forEach {
                    //if dla odzwiedzonych
                    var elem = it.first
                    if(path[synapseElemet.indexOf(elem)] == false) {
                        path[synapseElemet.indexOf(elem)] = true
                        var synapse = mSynapse()
                        node.join!!.node!!.node!!.synapses.add(synapse)
                        addElemToSynapse(elem, synapse)
                        var nextJoint = nextJoint(node.join!!, elem)
                        var nextElem = elem
                        while (joinIsNOTNode(nextJoint!!)) {
                            nextElem = nextElem(nextJoint, nextElem)
                            addElemToSynapse(nextElem, synapse)
                            path[synapseElemet.indexOf(nextElem)] = true
                            nextJoint = nextJoint(nextJoint, nextElem)
                        }
                        nextJoint.node!!.node!!.synapses.add(synapse)
                        var ode = mNode.nodeList
                        var x =0
                    }
                }
            }



        }



        private fun addElemToSynapse(element: Element,synapse: mSynapse)
        {
            when(element)
            {
                is Resistor -> synapse.resistorArray.add(mResistor().also { it.resistanceValue = (element as Resistor).resistance })
                is TensionSource -> synapse.tensionArray.add(mTensionSource(element.tensionValue,null))
                is FlowSource -> synapse.flowSource = mFlowSource(element.flowValue,null)

            }
        }

        private fun joinIsNOTNode(joint: Joint):Boolean
        {
          return joint.elementsJoined.size < 3
        }

        private fun nextElem(joint:Joint, element: Element):Element
        {
            if(joint.elementsJoined[1].first == element)
                return joint.elementsJoined[0].first
            return joint.elementsJoined[1].first

        }

        private fun nextJoint(joint: Joint, element: Element):Joint?
        {
            if(element.startJoint == joint) return  element.endJoint
            else return element.startJoint
        }
    }
    var node:mNode? = null
    var join:Joint? = null
}