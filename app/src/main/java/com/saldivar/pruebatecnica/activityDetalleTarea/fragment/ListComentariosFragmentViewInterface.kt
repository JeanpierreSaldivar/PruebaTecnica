package com.saldivar.pruebatecnica.activityDetalleTarea.fragment

import com.saldivar.pruebatecnica.db.Comentarios

interface ListComentariosFragmentViewInterface {
    fun comentariosObtenidos(listaComentarios:List<Comentarios>)
}