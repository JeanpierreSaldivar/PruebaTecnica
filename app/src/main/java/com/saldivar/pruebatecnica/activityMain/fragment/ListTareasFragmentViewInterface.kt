package com.saldivar.pruebatecnica.activityMain.fragment

import android.content.Context
import com.saldivar.pruebatecnica.db.Tareas

interface ListTareasFragmentViewInterface {
    fun resultadoConsultaListTareas(listaTareas:List<Tareas>)

    fun respuestaConsultaEstado(listaTareas:List<Tareas>,id:Int,context: Context)
}