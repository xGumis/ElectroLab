package com.example.canvastest.model

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
                        synapse.from = node.node
                        node.join!!.node!!.node!!.synapses.add(synapse)
                        addElemToSynapse(elem, synapse,it.second)
                        var nextJoint = nextJoint(node.join!!, elem)
                        var nextElem = elem
                        while (joinIsNOTNode(nextJoint!!)) {
                            nextElem = nextElem(nextJoint, nextElem)
                            addElemToSynapse(nextElem, synapse, doesItFlowRightWay(nextJoint,nextElem))
                            path[synapseElemet.indexOf(nextElem)] = true
                            nextJoint = nextJoint(nextJoint, nextElem)
                        }
                        synapse.to = nextJoint.node!!.node
                        nextJoint.node!!.node!!.synapses.add(synapse)
                        if(synapse.flowSource?.from == null)synapse.flowSource?.from = synapse.to
                        synapse.tensionArray.forEach {
                            if(it.from == null)it.from = synapse.to
                        }
                        var ode = mNode.nodeList
                        var x =0
                    }
                }
            }



        }



        private fun addElemToSynapse(element: Element,synapse: mSynapse,start: Boolean)
        {
            var node: mNode? = null
            if(start) node = synapse.from
            when(element)
            {
                is Resistor -> synapse.resistorArray.add(mResistor().also { it.resistanceValue = (element as Resistor).resistance })
                is TensionSource -> synapse.tensionArray.add(mTensionSource(element.tensionValue,node))
                is FlowSource -> synapse.flowSource = mFlowSource(element.flowValue,node)

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
        private fun doesItFlowRightWay(joint:Joint, element: Element):Boolean
        {
            if(joint.elementsJoined[1].first == element)
                return joint.elementsJoined[1].second
            return joint.elementsJoined[0].second

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