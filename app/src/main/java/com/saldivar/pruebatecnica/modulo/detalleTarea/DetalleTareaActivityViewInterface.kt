package com.saldivar.pruebatecnica.modulo.detalleTarea

import com.saldivar.pruebatecnica.db.Tareas

interface DetalleTareaActivityViewInterface {
    fun datosNuevosTareaObtenido(tareaActualizada:List<Tareas>)
}