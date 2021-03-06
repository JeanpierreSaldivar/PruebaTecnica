package com.saldivar.pruebatecnica.db

import androidx.room.*

@Dao
interface RoomDao {
    //Tareas
    @Query ("SELECT * FROM tareas WHERE id = (SELECT MAX(ID) FROM tareas) limit 1")
    fun consultarUltimaTarea():Tareas

    @Insert
    fun insertTarea(tarea: Tareas)

    @Query("Delete from tareas where id =:idTarea")
    fun deleteTarea(idTarea:Int)

    @Query("SELECT * FROM tareas where estado=:estadoBuscar ")
    fun getAllTareas(estadoBuscar:Boolean):MutableList<Tareas>

    @Query("UPDATE tareas set estado = :boolean  where id=:idEntrega ")
    fun updateEstado(boolean: Boolean,idEntrega:Int)

    @Query("select * from tareas where id=:idEntrega limit 1")
    fun consultarTarea(idEntrega:Int):Tareas

    @Query("UPDATE tareas set titulo=:tituloEnviado, descripcion=:descripcionEnviado,finalizacion=:finalizacionEnviado where id =:idEnviado")
    fun actualizarTarea(tituloEnviado:String,descripcionEnviado:String,finalizacionEnviado:String,idEnviado:Int)

    //Comentarios
    @Insert
    fun insertComentario(comentario: Comentarios)

    @Query("SELECT * FROM comentarios where id_tarea = :id")
    fun getAllComentarios(id:Int):List<Comentarios>

    @Query("Delete from comentarios where id_tarea=:idTarea")
    fun eliminarComentarios(idTarea:Int)
}