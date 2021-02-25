package com.saldivar.pruebatecnica.activityMain.fragment

import android.content.Context
import com.saldivar.pruebatecnica.db.RoomDB
import com.saldivar.pruebatecnica.db.Tareas

class ListTareasFragmentModel(private  val presenter: ListTareasFragmentPresenterInterface):
    ListTareasFragmentModelInterface {
    override fun consultarListTareas(context: Context,estadoBuscar:Boolean) {
        val listaTareas : List<Tareas> = RoomDB.getDataBase(context).roomDAO().getAllTareas(estadoBuscar)
        respuestaConsultaListTareas(listaTareas)
    }
    private fun respuestaConsultaListTareas(listaTareas: List<Tareas>) {
        presenter.resultadoConsultaListTareas(listaTareas)
    }


    override fun insertarDatosDefecto(context: Context, listObject: MutableList<Tareas>) {
        for (i in 0 until listObject.size){
            RoomDB.getDataBase(context).roomDAO().insertTarea(listObject[i])
        }
    }

    override fun consultarEstado(id: Int, context: Context) {
        val listaTareas : List<Tareas> =RoomDB.getDataBase(context).roomDAO().consultarEstado(id)
        respuestaConsultaEstado(listaTareas,id,context)
    }

    private fun respuestaConsultaEstado(listaTareas: List<Tareas>, id: Int,context: Context) {
        presenter.respuestaConsultaEstado(listaTareas,id,context)
    }

    override fun actualizarEstadoTarea(id:Int,context: Context,estado:Boolean) {
        RoomDB.getDataBase(context).roomDAO().updateEstado(estado,id)
    }


}
