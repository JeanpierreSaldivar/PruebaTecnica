package com.saldivar.pruebatecnica.activityDetalleTarea

import com.saldivar.pruebatecnica.db.Tareas

interface DetalleTareaActivityViewInterface {
    fun datosNuevosTareaObtenido(tareaActualizada:List<Tareas>)
}