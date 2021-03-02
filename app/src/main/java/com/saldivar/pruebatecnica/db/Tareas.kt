package com.saldivar.pruebatecnica.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "tareas")
class Tareas(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Int,
    @ColumnInfo(name = "titulo")
    @NotNull
var titulo:String,
    @ColumnInfo(name = "descripcion")
    @NotNull
var descripcion:String,
    @ColumnInfo(name = "creacion")
    @NotNull
var creacion:String,
    @ColumnInfo(name = "finalizacion")
    @NotNull
var finalizacion:String,
    @ColumnInfo(name = "estado")
    @NotNull
var estado:Boolean)
