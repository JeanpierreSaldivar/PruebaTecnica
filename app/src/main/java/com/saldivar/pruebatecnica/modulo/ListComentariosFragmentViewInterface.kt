package com.saldivar.pruebatecnica.modulo

import com.saldivar.pruebatecnica.db.Comentarios

interface ListComentariosFragmentViewInterface {
    fun comentariosObtenidos(listaComentarios:List<Comentarios>)
}