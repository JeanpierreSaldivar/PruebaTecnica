package com.saldivar.pruebatecnica.modulo.HomeActivity.util

import android.content.Context
import com.saldivar.pruebatecnica.db.Tareas

interface RecyclerTareasListener {
    fun onClick(tarea: Tareas, position :Int)
    fun change(tarea: Tareas, position: Int)
}

