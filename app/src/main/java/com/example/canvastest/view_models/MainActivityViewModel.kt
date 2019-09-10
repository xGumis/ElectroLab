package com.example.canvastest.view_models

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.canvastest.database.*
import com.example.canvastest.elements.Element
import com.example.canvastest.elements.FlowSource
import com.example.canvastest.elements.Resistor
import com.example.canvastest.elements.TensionSource
import org.jetbrains.anko.doAsync
import java.util.ArrayList

class MainActivityViewModel(private val database: AppDatabase):ViewModel() {


    private val schemaDao = database.schemaDao()
    private val resistorDao = database.resistorDao()
    private val tensionDao = database.tensionDao()
    private val flowDao = database.flowDao()

    val schemas: LiveData<List<Schema>> =database.schemaDao().getSchemas()// Transformations.map(database.schemaDao().getSchemas(), ::t);

    var usingSchema:Schema? = null


    fun save(elements:Array<Element>)
    {
        //todo clear using schema elements, save new
        usingSchema?.let {
        doAsync {
            resistorDao.delete(resistorDao.getResistorBySchema(usingSchema!!.id!!))
            flowDao.delete(flowDao.getFlowBySchema(usingSchema!!.id!!))
            tensionDao.delete(tensionDao.getTensionBySchema(usingSchema!!.id!!))
            elements.forEach {
                when (it) {
                    is Resistor -> {
                        val res = elemtntToResistorEntity(it, usingSchema!!.id)
                        resistorDao.insert(res)
                    }
                    is FlowSource -> {
                        flowDao.insert(elemtntToFlowEntity(it, usingSchema!!.id))
                    }
                    is TensionSource -> {
                        tensionDao.insert(elemtntToTensionnEntity(it, usingSchema!!.id))
                    }
                }

            }
        }
        }
    }
    fun save(name: String, elements:Array<Element>)
    {
        doAsync {

            val schemaID = schemaDao.insert(Schema((name)))
            usingSchema = Schema(name,schemaID)
            save(elements)

        }
    }

    private fun elemtntToResistorEntity(element: Element, id:Long? = null):ResistorEntity
    {
        return ResistorEntity().also {rE ->
            rE.sp = element.startPoint
            rE.ep = element.endPoint
            rE.resistance = (element as Resistor).resistance
            id?.let {
                rE.schemaId= id
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
    fun load(schema: Schema):Array<Element>
    {
        val elements = mutableListOf<Element>()
        resistorDao.getResistorBySchema(schema.id!!.toLong()).forEach {
            elements.add(Resistor(it.sp!!,it.ep!!,it.resistance!!))
        }
        tensionDao.getTensionBySchema(schema.id!!.toLong()).forEach {
            elements.add(TensionSource(it.sp!!,it.ep!!,it.tension!!))
        }
        flowDao.getFlowBySchema(schema.id!!.toLong()).forEach {
            elements.add(FlowSource(it.sp!!,it.ep!!,it.flow!!))
        }


        return elements.toTypedArray()
    }
}