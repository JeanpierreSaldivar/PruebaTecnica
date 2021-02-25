package com.saldivar.pruebatecnica.activityMain.fragment

import com.saldivar.pruebatecnica.activityMain.TareaObject
import com.saldivar.pruebatecnica.db.Tareas

class MapperListTareas {
    fun listTareas(listaTareas: List<Tareas>):MutableList<TareaObject>{
        val listObject = mutableListOf<TareaObject>()
        var ordenListObject = 0
        for (i in (listaTareas.size) downTo  1){
            val objectVal = TareaObject(
                listaTareas[i-1].id,
                listaTareas[i-1].titulo,
                listaTareas[i-1].descripcion,
                listaTareas[i-1].creacion,
                listaTareas[i-1].finalizacion,
                listaTareas[i-1].estado)
            listObject.add(ordenListObject,objectVal)
            ordenListObject += 1
        }
        return listObject
    }

}
