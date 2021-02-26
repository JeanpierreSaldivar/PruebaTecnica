package com.saldivar.pruebatecnica.activityDetalleTarea

import android.content.Context
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas

interface DetalleTareaActivityPresenterInterface {
    fun insertarComentarioBD(context: Context, list:MutableList<Comentarios>)
    fun actualizarTarea(titulo:String,descripcion:String,finalizacion:String,id:Int,context:Context)
    fun consultarDatosNuevos(context:Context,id:Int)
    fun datosNuevosTareaObtenido(tareaActualizada:List<Tareas>)
}