package com.saldivar.pruebatecnica.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDao {

    @Insert
    fun insertTarea(tarea: Tareas)

    @Query("Delete From tareas")
    fun deletTareas()

    @Query("SELECT * FROM tareas where estado=:estadoBuscar")
    fun getAllTareas(estadoBuscar:Boolean):List<Tareas>

    @Query("UPDATE tareas set estado = :boolean  where id=:idEntrega   ")
    fun updateEstado(boolean: Boolean,idEntrega:Int)

    @Query("select * from tareas where id=:idEntrega")
    fun consultarEstado(idEntrega:Int):List<Tareas>
}