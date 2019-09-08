package com.example.canvastest

import android.app.AlertDialog
import android.content.ClipDescription
import android.content.DialogInterface
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.canvastest.elements.*
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import android.util.Log
import android.view.DragEvent
import android.view.MenuItem
import android.view.View
import com.example.canvastest.Model.Node2
import com.example.canvastest.model.*
import com.example.canvastest.views.UpMenuItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.circuit_view.*
import kotlinx.android.synthetic.main.main_content.*
import java.lang.Exception
import kotlin.collections.ArrayList
//import android.R
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.example.canvastest.utilites.InjectorUtils
import com.example.canvastest.view_models.MainActivityViewModel
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnDragListener {

    private val viewModel:MainActivityViewModel by viewModels {
        InjectorUtils.provideMainActivityViewModelFactory(this)
    }

    private var buttonsVisibility:Boolean= false
        set(value) {
            field = value
            if (value) {
                fab_delete.show()
                fab_edit.show()
                fab_play.hide()
            }
            else {
                fab_delete.hide()
                fab_edit.hide()
                fab_play.show()
            }

        }

    private var nodeMap = mutableMapOf<Int, mNode>()
    private var synapses: MutableList<mSynapse> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        circuitView.addElement(FlowSource(Point(100, 100), Point(300, 100)))
        circuitView.addElement(Resistor(Point(100, 300), Point(300, 300)))
        circuitView.addElement(TensionSource(Point(100, 500), Point(300, 500)))

        nav_view.setNavigationItemSelectedListener(this)
        buttonsVisibility = false
        circuitView.onSelectedChange = {
            buttonsVisibility = it
        }
        fab_delete.setOnClickListener {
            circuitView.onDeleteSelectedView()
        }
        fab_edit.setOnClickListener {
            circuitView.onEditSelectedView()
        }
        fab_play.setOnClickListener{
            calculate()
        }
        setMenu()
        mainlayout.setOnDragListener(this)

    }


    private fun setMenu()
    {
        electricElementlayout.removeAllViews()
        electricElementlayout.addView(UpMenuItem(applicationContext, UpMenuItem.Companion.Type.Resistor))
        electricElementlayout.addView(UpMenuItem(applicationContext, UpMenuItem.Companion.Type.FlowSource))
        electricElementlayout.addView(UpMenuItem(applicationContext, UpMenuItem.Companion.Type.TensionSource))
        electricElementlayout.invalidate()
        mainlayout.invalidate()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_play -> {
                onSave()
                //circuitView.addElement(Resistor(Point(100, 100), Point(300, 100)))
            }
            R.id.nav_flow -> {
                circuitView.addElement(FlowSource(Point(100, 100), Point(300, 100)))
            }
            R.id.nav_tension -> {
                circuitView.addElement(TensionSource(Point(100, 100), Point(300, 100)))

            }
            R.id.nav_play->{
                calculate()
            }
        }
        circuitView.invalidate()
        val drawerLayout: androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun calculate()
    {
        try {
            mNode.nodeList = mutableListOf()
            findSynapses()
            circuitView.findSynapses()
            Node2.createMatrix()
            mNode.groundedNode = mNode.nodeList[0]
            mNode.updatePotential()
            circuitView.invalidate()
        }catch (e:Exception){
            var x =0
        }
    }

    override fun onDrag(v: View, event: DragEvent): Boolean {
        val action = event.action
        // Handles each of the expected events
        when (action) {

            DragEvent.ACTION_DRAG_STARTED -> {
                // Determines if this View can accept the dragged data
                return (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
            }

            DragEvent.ACTION_DRAG_ENTERED -> {
                v.invalidate()
                return true
            }

            DragEvent.ACTION_DRAG_LOCATION ->
                // Ignore the event
                return true

            DragEvent.ACTION_DRAG_EXITED -> {
                v.invalidate()
                return true
            }

            DragEvent.ACTION_DROP -> {
                val item = event.clipData.getItemAt(0)
                val dragData = item.text.toString()
                v.invalidate()
                try {
                    val sp = Point(event.x.toInt() - 75,event.y.toInt()-((event.localState as View).height*1.5).toInt())
                    val ep = Point(event.x.toInt() + 75,event.y.toInt()-((event.localState as View).height*1.5).toInt())
                    when(dragData) {
                        UpMenuItem.Companion.Type.Resistor.toString()->{
                            circuitView.addElement(Resistor(sp,ep)) }
                        UpMenuItem.Companion.Type.TensionSource.toString()->{
                            circuitView.addElement(TensionSource(sp,ep)) }
                        UpMenuItem.Companion.Type.FlowSource.toString()->{
                            circuitView.addElement(FlowSource(sp,ep)) }
                    }
                    circuitView.invalidate()

                }catch (e: Exception)
                {
                    var x=0
                }
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                v.invalidate()
                return true
            }
        }
        return false
    }

    private fun getMidlePoitFromDragingupMenu(event: DragEvent, view: View):Point
    {
        return Point(event.x.toInt() - view.width/2,event.y.toInt()-view.height/2)
    }

    private fun onSave()
    {
        val builder = AlertDialog.Builder(this@MainActivity)
        val inflater = this.layoutInflater
        val v = inflater.inflate(R.layout.dialog_fragment_save_lab, null)  // this line
        builder.setView(v)
        builder.setPositiveButton("Zapisz",
            DialogInterface.OnClickListener { dialog, id ->
               Log.d("alert","zapis")
                viewModel.save(v.findViewById<EditText>(R.id.editText_name).text.toString(),circuitView.elements.toTypedArray())
            })
        builder.setNegativeButton("Anuluj",
            DialogInterface.OnClickListener { dialog, id ->
                Log.d("alert","anulowano")
            })
        val dialog = builder.create()

        dialog.show()

    }

    private fun findSynapses() {
        synapses.clear()
        nodeMap.clear()
        findNodes()
        var chckd = mutableMapOf<Int, ArrayList<Int>>()
        nodeMap.forEach { chckd[it.key] = arrayListOf() }
        nodeMap.forEach {
            val start = it.key
            circuitView.joints[start].elementsJoined.forEach {
                var ind = circuitView.joints[start].elementsJoined.indexOf(it)
                if (!chckd[start]!!.contains(ind)) {
                    chckd[start]!!.add(ind)
                    var pair = it
                    var done = false
                    while (!done) {
                        if (pair.second)
                            if (pair.first.endJoint != null) {
                                pair.first.endJoint?.let { joint ->
                                    var i = circuitView.joints.indexOf(joint)
                                    if ( nodeMap.keys.contains(i)) {
                                        ind = circuitView.joints[i].elementsJoined.indexOf(Pair(pair.first, false))
                                        chckd[i]!!.add(ind)
                                        val synapse = mSynapse()
                                        synapse.to = nodeMap[i]
                                        synapse.from = nodeMap[start]
                                        synapses.add(synapse)
                                        done = true
                                    } else {
                                        joint.elementsJoined.forEach {
                                            if (pair != it)
                                                pair = it
                                        }
                                    }
                                }
                            } else break
                        else if (pair.first.startJoint != null) {
                            pair.first.startJoint?.let { joint ->
                                var i = circuitView.joints.indexOf(joint)
                                if ( nodeMap.keys.contains(i)) {
                                    ind = circuitView.joints[i].elementsJoined.indexOf(Pair(pair.first, true))
                                    chckd[i]!!.add(ind)
                                    val synapse = mSynapse()
                                    synapse.to = nodeMap[i]
                                    synapse.from = nodeMap[start]
                                    synapses.add(synapse)
                                    done = true
                                } else {
                                    joint.elementsJoined.forEach {
                                        if (pair != it)
                                            pair = it
                                    }
                                }
                            }
                        } else break
                    }
                }
            }
        }
    }


    private fun findNodes(){
        for (i in 0..circuitView.joints.count() - 1)
            if (circuitView.joints[i].elementsJoined.count() >= 2) {
                nodeMap[i] = mNode()
            }
    }
}
