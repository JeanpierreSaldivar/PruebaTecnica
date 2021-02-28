package com.saldivar.pruebatecnica.modulo

import android.content.Context
import com.saldivar.pruebatecnica.db.Tareas

interface ListTareasFragmentModelInterface {
    fun consultarListTareas(context: Context,estadoBuscar:Boolean)

    fun insertarDatosDefecto(context: Context,listObject: MutableList<Tareas>)

    fun consultarEstado(id:Int,context: Context)
    fun actualizarEstadoTarea(id:Int,context: Context,estado:Boolean)

}