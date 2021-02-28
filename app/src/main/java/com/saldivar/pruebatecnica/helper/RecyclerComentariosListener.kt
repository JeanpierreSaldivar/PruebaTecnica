package com.saldivar.pruebatecnica.helper

import com.saldivar.pruebatecnica.db.Tareas

interface RecyclerComentariosListener {

    fun onClick(flight: Tareas, position :Int)
}