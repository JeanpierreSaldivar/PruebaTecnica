package com.saldivar.pruebatecnica.activityMain.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.activityMain.TareaObject
import com.saldivar.pruebatecnica.activityMain.TareasAdapter
import com.saldivar.pruebatecnica.activityMain.estadoVisualizadorTareas
import com.saldivar.pruebatecnica.db.Tareas
import kotlinx.android.synthetic.main.fragment_list_tareas.view.*
import kotlinx.android.synthetic.main.item_recyler_tareas.*


class ListTareasFragment : Fragment(), ListTareasFragmentViewInterface {
    private lateinit var presenter: ListTareasFragmentPresenterInterface
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: TareasAdapter
    private val layoutManager by lazy { LinearLayoutManager(context) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.fragment_list_tareas, container, false)
        presenter = ListTareasFragmentPresenter(this)
        recycler = rootview.recycler_tareas as RecyclerView
        consultar()

        return rootview
    }

    companion object {
        fun newInstance(): ListTareasFragment = ListTareasFragment()
    }

    private fun consultar() {
        presenter.consultarListTareas(this.activity!!,estadoVisualizadorTareas.ojo)
    }

    override fun resultadoConsultaListTareas(listaTareas: List<Tareas>) {
        if(listaTareas.isNotEmpty()){
            val tareaObject = MapperListTareas().listTareas(listaTareas)
            setRecyclerView(tareaObject)
        }
        else{
            insertDatosDefecto()
            consultar()
        }
    }


    private fun insertDatosDefecto() {
        val listObject = mutableListOf<Tareas>()
        val objectVal0 = Tareas(0,"Ejercicio para casa","Resolver problemas diversos sobre algebra",
            "17/02","22/02",false)
        listObject.add(0,objectVal0)
        val objectVal1 = Tareas(0,"Tarea de Química","Resolver problemas sobre teoremas sobre la" +
                "tabla periodica","17/02","22/02",false)
        listObject.add(1,objectVal1)
        val objectVal2 = Tareas(0,"Tarea de Matemáticas","Resolver problemas sobre teoremas de" +
                "pitagoras","17/02","22/02",false)
        listObject.add(2,objectVal2)
        val objectVal3 = Tareas(0,"Tarea de Biologia","Resolver problemas sobre Biologia"
            ,"17/02","22/02",true)
        listObject.add(3,objectVal3)
        insertarBD(listObject)
    }

    private fun insertarBD(listObject: MutableList<Tareas>) {
        presenter.insertarDatosDefecto(this.activity!!,listObject)
    }

    private fun setRecyclerView(datosTarea: MutableList<TareaObject>) {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = layoutManager
        adapter=(TareasAdapter(
            datosTarea,
            object :
                RecyclerTareasListener {
                override fun onClick(flight: TareaObject, position: Int) {

                }

                override fun onDelete(flight: TareaObject, position: Int) {
                }

                override fun change(flight: TareaObject, position: Int) {
                    consultarEstado(flight)
                }

            }))
        recycler.adapter = adapter
    }

    private fun consultarEstado(flight: TareaObject) {
        presenter.consultarEstado(flight.id,this.activity!!)
    }
    override fun respuestaConsultaEstado(lista: List<Tareas>,id:Int) {
        val list = MapperListTareas().listTareas(lista)
        if (list[0].estado){
            updateEstado(list[0],false)
        }
        else{
            updateEstado(list[0],true)
        }
    }

    private fun updateEstado(flight: TareaObject, estado: Boolean) {
        presenter.actualizarEstadoTarea(flight.id,this.activity!!,estado)
    }

}