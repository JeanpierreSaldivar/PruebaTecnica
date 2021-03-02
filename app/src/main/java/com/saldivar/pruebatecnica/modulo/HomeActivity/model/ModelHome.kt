package com.saldivar.pruebatecnica.modulo.HomeActivity.model

import com.saldivar.pruebatecnica.db.InstanciaBD
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.UtilHome
import com.saldivar.pruebatecnica.modulo.HomeActivity.mvp.HomeMVP
import com.saldivar.pruebatecnica.modulo.HomeActivity.presenter.PresenterHome

class ModelHome(private val presenter: PresenterHome): HomeMVP.Model{
    override fun insertarListaTareasDefectoBD(mutableList: MutableList<Tareas>) {
        for (i in 0 until mutableList.size){
            InstanciaBD.dbRoom.insertTarea(mutableList[i])
        }
    }

    override fun consultarListaTareas(estadoOjo:Boolean):MutableList<Tareas> {
        return InstanciaBD.dbRoom.getAllTareas(estadoOjo)
    }

    override fun consultarEstadoTarea(idTarea: Int): Tareas {
        return InstanciaBD.dbRoom.consultarTarea(idTarea)
    }

    override fun updateEstado(tarea: Tareas, valorActulizar: Boolean) {
        InstanciaBD.dbRoom.updateEstado(valorActulizar,tarea.id)
    }

    override fun insertarNuevaTarea(nuevaTarea: Tareas) {
        InstanciaBD.dbRoom.insertTarea(nuevaTarea)
    }

    override fun consultarUltimaTareaInsertada(): Tareas {
        return InstanciaBD.dbRoom.consultarUltimaTarea()
    }


}