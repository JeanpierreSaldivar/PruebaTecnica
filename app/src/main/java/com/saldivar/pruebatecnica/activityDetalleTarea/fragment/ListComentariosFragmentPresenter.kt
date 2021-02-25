package com.saldivar.pruebatecnica.activityDetalleTarea.fragment

import android.content.Context
import com.saldivar.pruebatecnica.db.Comentarios


class ListComentariosFragmentPresenter(private val view: ListComentariosFragmentViewInterface) :
    ListComentariosFragmentPresenterInterface {
    private val model: ListComentariosFragmentModelInterface
    init {
        model = ListComentariosFragmentModel(this)
    }

    override fun getAllComentarios(context: Context, id: Int) {
        model.getAllComentarios(context,id)
    }

    override fun comentariosObtenidos(listaComentarios: List<Comentarios>) {
        view.comentariosObtenidos(listaComentarios)
    }

    override fun insertarDatosDefecto(context: Context, listObject: MutableList<Comentarios>) {
        model.insertarDatosDefecto(context,listObject)
    }
}