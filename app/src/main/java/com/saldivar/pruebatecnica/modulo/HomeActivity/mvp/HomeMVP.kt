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
        fun consultarListaTareas():MutableList<Tareas>
        fun consultarEstadoTarea(idTarea:Int):List<Tareas>
        fun updateEstado(tarea:Tareas, valorActulizar:Boolean)
        fun  insertarNuevaTarea(nuevaTarea:Tareas)
        fun consultarUltimaTareaInsertada():List<Tareas>
    }
    interface Presenter{
        fun cambiarEstadoOjo():Drawable
        fun consultarListTareas(recyclerView: RecyclerView)
        fun insertarNuevaTarea(nuevaTarea:Tareas)
        fun validacion(titulo:EditText,contenido:EditText,fecha:EditText,mAlerDialog:androidx.appcompat.app.AlertDialog,recyclerView: RecyclerView)
    }
    interface View{
        fun nextActivity()
    }


}