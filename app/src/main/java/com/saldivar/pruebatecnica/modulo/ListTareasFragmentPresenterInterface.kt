package com.saldivar.pruebatecnica.modulo

import android.content.Context
import com.saldivar.pruebatecnica.db.Tareas

interface ListTareasFragmentPresenterInterface {
    fun consultarListTareas(context:Context,estadoBuscar:Boolean)
    fun resultadoConsultaListTareas(listaTareas:List<Tareas>)

    fun insertarDatosDefecto(context: Context,listObject: MutableList<Tareas>)

    fun consultarEstado(id:Int,context: Context)
    fun actualizarEstadoTarea(id:Int,context: Context,estado:Boolean)

    fun respuestaConsultaEstado(listaTareas:List<Tareas>,id:Int,context: Context)
}