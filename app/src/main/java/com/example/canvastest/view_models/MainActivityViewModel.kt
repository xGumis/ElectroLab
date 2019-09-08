package com.example.canvastest.view_models

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.canvastest.database.*
import com.example.canvastest.elements.Element
import com.example.canvastest.elements.FlowSource
import com.example.canvastest.elements.Resistor
import com.example.canvastest.elements.TensionSource
import org.jetbrains.anko.doAsync
import java.util.ArrayList

class MainActivityViewModel(val database: AppDatabase):ViewModel() {


    private val schemaDao = database.schemaDao()
    private val resistorDao = database.resistorDao()
    private val tensionDao = database.tensionDao()
    private val flowDao = database.flowDao()

    fun save(name: String, elements:Array<Element>)
    {
        doAsync {
            val schemaID = schemaDao.insert(Schema((name)))
            elements.forEach {
                when(it){
                    is Resistor->{resistorDao.insert(elemtntToResistorEntity(it,schemaID))}
                    is FlowSource->{flowDao.insert(elemtntToFlowEntity(it,schemaID))}
                    is TensionSource->{tensionDao.insert(elemtntToTensionnEntity(it,schemaID))}
                }

            }
        }
    }

    private fun elemtntToResistorEntity(element: Element, id:Long? = null):ResistorEntity
    {
        return ResistorEntity().apply {
            sp = element.startPoint
            ep = element.endPoint
            resistance = (element as Resistor).resistance
            id?.let {
                schemaId = id
            }
        }
    }
    private fun elemtntToFlowEntity(element: Element, id:Long? = null):FlowEntity
    {
        return FlowEntity().apply {
            sp = element.startPoint
            ep = element.endPoint
            flow = (element as FlowSource).flowValue
            id?.let {
                schemaId = id
            }
        }
    }
    private fun elemtntToTensionnEntity(element: Element, id:Long? = null):TensionEntity
    {
        return TensionEntity().apply {
            sp = element.startPoint
            ep = element.endPoint
            tension = (element as TensionSource).tensionValue
            id?.let {
                schemaId = id
            }
        }
    }
}