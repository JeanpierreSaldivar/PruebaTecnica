package com.saldivar.pruebatecnica.modulo.detalleTarea.mvp

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas

interface DetalleTareaMVP {
    interface Presenter{
            fun getAllComentarios(recyclerView: RecyclerView)
            fun enviarNuevoComentario(comentario:String,recyclerView: RecyclerView)
            fun validacion(titulo:String,contenido:String,fecha:String,mAlertDialog:AlertDialog,recyclerView:RecyclerView)
            fun eliminarComentarios()
            fun eliminarTarea()
    }

    interface Model{
        fun getAllComentarios():List<Comentarios>
        fun insertarDatosDefecto(listObject: MutableList<Comentarios>)
        fun insertarComentarioBD(comentarioNuevo:Comentarios)
        fun updateTarea(titulo: String, descripcion: String, finalizacion: String, id: Int)
        fun consultarDatosNuevosTarea( id: Int):List<Tareas>
        fun eliminarComentarios(id:Int)
        fun eliminarTarea(id:Int)
    }
    interface View{
        fun cantidadComentarios(cantidadComentarios: String)
        fun setearDatosVista()
    }
}