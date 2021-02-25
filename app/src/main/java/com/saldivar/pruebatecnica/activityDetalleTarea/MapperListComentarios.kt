package com.saldivar.pruebatecnica.activityDetalleTarea

import com.saldivar.pruebatecnica.activityMain.TareaObject
import com.saldivar.pruebatecnica.db.Comentarios

class MapperListComentarios {
    fun listComentarios(listaComentarios: List<Comentarios>):MutableList<ComentarioObject>{
        val listObject = mutableListOf<ComentarioObject>()
        var ordenListObject = 0
        for (i in (listaComentarios.size) downTo  1){
            val objectVal = ComentarioObject(
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