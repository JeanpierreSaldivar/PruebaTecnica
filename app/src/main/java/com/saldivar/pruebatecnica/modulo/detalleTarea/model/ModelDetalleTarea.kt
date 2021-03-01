package com.saldivar.pruebatecnica.modulo.detalleTarea.model
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.InstanciaBD
import com.saldivar.pruebatecnica.db.RoomDB
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.UtilHome
import com.saldivar.pruebatecnica.modulo.detalleTarea.mvp.DetalleTareaMVP
import com.saldivar.pruebatecnica.modulo.detalleTarea.presenter.PresenterDetalleTarea

class ModelDetalleTarea(private val presenter: PresenterDetalleTarea):DetalleTareaMVP.Model {

    override fun getAllComentarios(): List<Comentarios> {
        return InstanciaBD.dbRoom.getAllComentarios(UtilHome.id)
    }

    override fun insertarDatosDefecto(listObject: MutableList<Comentarios>) {
        for (i in 0 until listObject.size){
            InstanciaBD.dbRoom.insertComentario(listObject[i])
        }
    }

    override fun insertarComentarioBD(comentarioNuevo: Comentarios) {
        InstanciaBD.dbRoom.insertComentario(comentarioNuevo)
    }

    override fun updateTarea(titulo: String, descripcion: String, finalizacion: String, id: Int) {
        InstanciaBD.dbRoom.actualizarTarea(titulo,descripcion,finalizacion,id)
    }

    override fun consultarDatosNuevosTarea(id: Int):List<Tareas> {
        return InstanciaBD.dbRoom.consultarTarea(id)

    }

    override fun eliminarComentarios(id: Int) {
        InstanciaBD.dbRoom.eliminarComentarios(id)
    }

    override fun eliminarTarea(id: Int) {
        InstanciaBD.dbRoom.deleteTarea(id)
    }


}