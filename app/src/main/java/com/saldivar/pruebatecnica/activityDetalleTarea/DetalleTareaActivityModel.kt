package com.saldivar.pruebatecnica.activityDetalleTarea

import android.content.Context
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.RoomDB
import com.saldivar.pruebatecnica.db.Tareas

class DetalleTareaActivityModel(private  val presenter: DetalleTareaActivityPresenterInterface):
    DetalleTareaActivityModelInterface {
    override fun insertarComentarioBD(context: Context, list:MutableList<Comentarios>) {
        for (i in 0 until list.size){
            RoomDB.getDataBase(context).roomDAO().insertComentario(list[i])
        }
    }

    override fun actualizarTarea(titulo: String, descripcion: String, finalizacion: String, id: Int,context: Context) {
        RoomDB.getDataBase(context).roomDAO().actualizarTarea(titulo,descripcion,finalizacion,id)
    }

    override fun consultarDatosNuevos(context: Context, id: Int) {
        val tareaActualizada : List<Tareas> = RoomDB.getDataBase(context).roomDAO().consultarTarea(id)
        datosNuevosTareaObtenidos(tareaActualizada)
    }

    override fun eliminarComentarios(context: Context, id: Int) {
        RoomDB.getDataBase(context).roomDAO().eliminarComentarios(id)
    }

    override fun eliminarTarea(context: Context, id: Int) {
        RoomDB.getDataBase(context).roomDAO().deleteTarea(id)
    }

    private fun datosNuevosTareaObtenidos(tareaActualizada: List<Tareas>) {
        presenter.datosNuevosTareaObtenido(tareaActualizada)
    }
}