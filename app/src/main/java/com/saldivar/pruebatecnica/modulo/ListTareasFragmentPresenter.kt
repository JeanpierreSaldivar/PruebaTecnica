package com.saldivar.pruebatecnica.modulo

import android.content.Context
import com.saldivar.pruebatecnica.db.Tareas

class ListTareasFragmentPresenter(private val view: ListTareasFragmentViewInterface) :
    ListTareasFragmentPresenterInterface {
    private val model: ListTareasFragmentModelInterface

    override fun consultarListTareas(context: Context,estadoBuscar:Boolean) {
        model.consultarListTareas(context,estadoBuscar)
    }

    override fun resultadoConsultaListTareas(listaTareas: List<Tareas>) {
        view.resultadoConsultaListTareas(listaTareas)
    }

    override fun insertarDatosDefecto(context: Context, listObject: MutableList<Tareas>) {
        model.insertarDatosDefecto(context,listObject)
    }

    override fun consultarEstado(id: Int, context: Context) {
        model.consultarEstado(id,context)
    }

    override fun actualizarEstadoTarea(id: Int, context: Context, estado: Boolean) {
        model.actualizarEstadoTarea(id, context, estado)
    }

    override fun respuestaConsultaEstado(listaTareas: List<Tareas>, id: Int,context: Context) {
        view.respuestaConsultaEstado(listaTareas,id,context)
    }


    init {
        model = ListTareasFragmentModel(this)
    }
}