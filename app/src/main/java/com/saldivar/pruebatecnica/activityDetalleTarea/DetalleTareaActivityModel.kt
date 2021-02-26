package com.saldivar.pruebatecnica.activityDetalleTarea

import android.content.Context
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.RoomDB

class DetalleTareaActivityModel(private  val presenter: DetalleTareaActivityPresenterInterface):
    DetalleTareaActivityModelInterface {
    override fun insertarComentarioBD(context: Context, list:MutableList<Comentarios>) {
        for (i in 0 until list.size){
            RoomDB.getDataBase(context).roomDAO().insertComentario(list[i])
        }
    }
}