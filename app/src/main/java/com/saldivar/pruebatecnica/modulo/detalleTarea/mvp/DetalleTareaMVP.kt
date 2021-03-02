package com.saldivar.pruebatecnica.modulo.detalleTarea.mvp

import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas

interface DetalleTareaMVP {
    interface Presenter{
            fun getAllComentarios(idTarea: Int)
            fun enviarNuevoComentario(comentario:String,idTarea:Int)
            fun validacion(titulo:String,contenido:String,fecha:String,idTarea: Int):String
            fun eliminarComentarios(idTarea:Int)
    }

    interface Model{
        fun getAllComentarios(idTarea: Int):List<Comentarios>
        fun insertarComentarioBD(comentarioNuevo:Comentarios)
        fun updateTarea(titulo: String, descripcion: String, finalizacion: String, id: Int)
        fun consultarDatosNuevosTarea(id:Int):Tareas
        fun eliminarComentarios(id:Int)
    }
    interface View{
        fun setRecyclerView(datosComentario: MutableList<Comentarios>)
        fun setearDatosVista(nuevosDatos:Tareas)
    }
}