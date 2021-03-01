package com.saldivar.pruebatecnica.modulo.HomeActivity.util

import com.saldivar.pruebatecnica.db.Tareas

//Ordena las tareas de mayor a menor
class MapperListaTareas {
    fun ordenarTareasDeMayorMenor(listaTareas: List<Tareas>):MutableList<Tareas>{
        val listObject = mutableListOf<Tareas>()
        var ordenListObject = 0
        for (i in (listaTareas.size) downTo  1){
            val objectVal = Tareas(
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