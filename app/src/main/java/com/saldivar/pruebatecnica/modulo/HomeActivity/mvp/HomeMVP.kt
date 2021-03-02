package com.saldivar.pruebatecnica.modulo.HomeActivity.mvp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.TareasAdapter
import java.util.zip.Inflater

interface HomeMVP {
    interface Model{
        fun insertarListaTareasDefectoBD(mutableList:MutableList<Tareas>)
        fun consultarListaTareas(estadoOjo:Boolean):MutableList<Tareas>
        fun consultarEstadoTarea(idTarea:Int):Tareas
        fun updateEstado(tarea:Tareas, valorActulizar:Boolean)
        fun  insertarNuevaTarea(nuevaTarea:Tareas)
        fun consultarUltimaTareaInsertada():Tareas
        fun eliminarTarea(idTarea: Int)
    }
    interface Presenter{
        fun consultarListTareas(estadoOjo:Boolean):MutableList<Tareas>
        fun insertarNuevaTarea(nuevaTarea:Tareas)
        fun validacion(titulo:String,contenido:String,fecha:String):String
        fun consultaEstadoTarea(tareaID:Int):Boolean
        fun updateEstadoTarea(tarea:Tareas, valorActulizar:Boolean)
        fun eliminarTarea(idTarea:Int)
    }
    interface View{
        fun mostrarEnRecycler(list:MutableList<Tareas>)
        fun mostrarEnRecyclerAdd(ultimaTarea:Tareas)
    }


}