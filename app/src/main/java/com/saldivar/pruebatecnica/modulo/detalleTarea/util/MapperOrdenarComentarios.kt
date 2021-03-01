package com.saldivar.pruebatecnica.modulo.detalleTarea.util

import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas

class MapperOrdenarComentarios {
    fun ordenarComentariosDeMayorMenor(listaComentarios: List<Comentarios>):MutableList<Comentarios>{
        val listObject = mutableListOf<Comentarios>()
        var ordenListObject = 0
        for (i in (listaComentarios.size) downTo  1){
            val objectVal = Comentarios(
                    listaComentarios[i-1].idComentario,
                    listaComentarios[i-1].idTarea,
                    listaComentarios[i-1].user,
                    listaComentarios[i-1].comentario)
            listObject.add(ordenListObject,objectVal)
            ordenListObject += 1
        }
        return listObject
    }
}