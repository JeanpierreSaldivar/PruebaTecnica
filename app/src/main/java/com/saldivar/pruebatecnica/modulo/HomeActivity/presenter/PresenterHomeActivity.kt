package com.saldivar.pruebatecnica.modulo.HomeActivity.presenter

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.MyAplicationClass
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.RecyclerTareasListener
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.TareasAdapter
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.UtilHome
import com.saldivar.pruebatecnica.modulo.HomeActivity.model.ModelHomeActivity
import com.saldivar.pruebatecnica.modulo.HomeActivity.mvp.HomeMVP

class PresenterHomeActivity(private val view: HomeMVP.View) : HomeMVP.Presenter {
    private val model = ModelHomeActivity(this)


    override fun cambiarEstadoOjo(): Drawable {
        UtilHome.ojo = !UtilHome.ojo
        return if (UtilHome.ojo){
           MyAplicationClass.ctx!!.resources.getDrawable(R.drawable.ic_baseline_visibility_off_24)
        } else{
            MyAplicationClass.ctx!!.resources.getDrawable(R.drawable.ic_baseline_remove_red_eye_24)
        }
    }

    override fun consultarListTareas(recyclerView: RecyclerView) {
        val listaObtenida = model.consultarListaTareas()
            if (listaObtenida.isEmpty()){
                val mutableList = insertDatosDefecto()
                model.insertarListaTareasDefectoBD(mutableList)
                view.showToask("No hay Tareas disponibles")
            }else{
                val listaOrdenada = ordenarLista(listaObtenida)
                setRecyclerView(listaOrdenada,recyclerView)
            }
    }

    private fun ordenarLista(listaObtenida: MutableList<Tareas>):MutableList<Tareas> {
        for (i in 0 until listaObtenida.size){
        listaObtenida.add(i,listaObtenida[listaObtenida.size-i-1])}
        return listaObtenida
    }


    private fun insertDatosDefecto():MutableList<Tareas> {
        val listObject = mutableListOf<Tareas>()
        val objectVal0 = Tareas(1, "Ejercicio para casa", "Resolver problemas diversos sobre algebra",
            "17/02", "22/02", false)
        listObject.add(0, objectVal0)
        val objectVal1 = Tareas(2, "Tarea de Química", "Resolver problemas sobre teoremas sobre la" +
                "tabla periodica", "17/02", "22/02", false)
        listObject.add(1, objectVal1)
        val objectVal2 = Tareas(3, "Tarea de Matemáticas", "Resolver problemas sobre teoremas de" +
                "pitagoras", "17/02", "22/02", false)
        listObject.add(2, objectVal2)
        val objectVal3 = Tareas(4, "Tarea de Biologia", "Resolver problemas sobre Biologia", "17/02", "22/02", true)
        listObject.add(3, objectVal3)
         return listObject
    }

    private fun setRecyclerView(datosTarea: List<Tareas>,recycler: RecyclerView) {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(MyAplicationClass.ctx)
        recycler.adapter = (TareasAdapter(
            datosTarea,
            object :
                RecyclerTareasListener {
                override fun onClick(tarea: Tareas, position: Int) {
                    setearValores(tarea)
                    view.nextActivity()
                }
                override fun change(tarea: Tareas, position: Int) {
                    val tarea=model.consultarEstadoTarea(tarea.id)
                    updateEstadoTarea(tarea)
                }
            }))
    }

    private fun setearValores(tarea: Tareas) {
        UtilHome.id = tarea.id
        UtilHome.titulo = tarea.titulo
        UtilHome.creacion = tarea.creacion
        UtilHome.detalle = tarea.descripcion
        UtilHome.finalizacion = tarea.finalizacion
    }
    private fun updateEstadoTarea(tarea: List<Tareas>) {
        val valorActulizar= !tarea[0].estado
        model.updateEstado(tarea[0], valorActulizar)
    }
}