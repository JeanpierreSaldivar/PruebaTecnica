package com.saldivar.pruebatecnica.modulo.detalleTarea.model
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.InstanciaBD
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.modulo.detalleTarea.mvp.DetalleTareaMVP
import com.saldivar.pruebatecnica.modulo.detalleTarea.presenter.PresenterDetalleTarea

class ModelDetalleTarea(private val presenter: PresenterDetalleTarea):DetalleTareaMVP.Model {

    override fun getAllComentarios(idTarea: Int): List<Comentarios> {
        return InstanciaBD.dbRoom.getAllComentarios(idTarea)
    }


    override fun insertarComentarioBD(comentarioNuevo: Comentarios) {
        InstanciaBD.dbRoom.insertComentario(comentarioNuevo)
    }

    override fun updateTarea(titulo: String, descripcion: String, finalizacion: String, id: Int) {
        InstanciaBD.dbRoom.actualizarTarea(titulo,descripcion,finalizacion,id)
    }

    override fun consultarDatosNuevosTarea(id: Int):Tareas {
        return InstanciaBD.dbRoom.consultarTarea(id)
    }

    override fun eliminarComentarios(id: Int) {
        InstanciaBD.dbRoom.eliminarComentarios(id)
    }



}