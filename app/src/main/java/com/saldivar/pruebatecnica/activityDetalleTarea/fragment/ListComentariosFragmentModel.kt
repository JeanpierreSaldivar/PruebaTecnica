package com.saldivar.pruebatecnica.activityDetalleTarea.fragment

import android.content.Context
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.RoomDB


class ListComentariosFragmentModel(private  val presenter: ListComentariosFragmentPresenterInterface):
    ListComentariosFragmentModelInterface {
    override fun getAllComentarios(context: Context, id: Int) {
        val listaComentarios : List<Comentarios> = RoomDB.getDataBase(context).roomDAO().getAllComentarios(id)
        comentariosObtenidos(listaComentarios)
    }

    override fun insertarDatosDefecto(context: Context, listObject: MutableList<Comentarios>) {
        for (i in 0 until listObject.size){
            RoomDB.getDataBase(context).roomDAO().insertComentario(listObject[i])
        }
    }

    private fun comentariosObtenidos(listaComentarios: List<Comentarios>) {
        presenter.comentariosObtenidos(listaComentarios)
    }
}