package com.saldivar.pruebatecnica.modulo.HomeActivity.mvp

import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.TareasAdapter

interface HomeMVP {
    interface Presenter{
        fun cambiarEstadoOjo():Drawable
        fun consultarListTareas(recyclerView: RecyclerView)
    }
    interface View{
        fun nextActivity()
        fun showToask(message:String)
    }

    interface Model{
        fun insertarListaTareasDefectoBD(mutableList:MutableList<Tareas>)
        fun consultarListaTareas():MutableList<Tareas>
        fun consultarEstadoTarea(idTarea:Int):List<Tareas>
       fun updateEstado(tarea:Tareas, valorActulizar:Boolean)
    }
}