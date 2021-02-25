package com.saldivar.pruebatecnica.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comentarios")
class Comentarios (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_comentario")
    var id:Int = 0,
    @ColumnInfo(name = "user")
    var user:String?= null,
    @ColumnInfo(name = "comentario")
    var comentario:String?= null,
)