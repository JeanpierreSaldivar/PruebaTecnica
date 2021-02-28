package com.saldivar.pruebatecnica.modulo

import android.content.Context
import com.saldivar.pruebatecnica.db.Comentarios

interface ListComentariosFragmentPresenterInterface {
    fun getAllComentarios(context:Context,id:Int)
    fun comentariosObtenidos(listaComentarios:List<Comentarios>)

    fun insertarDatosDefecto(context:Context,listObject:MutableList<Comentarios>)
}