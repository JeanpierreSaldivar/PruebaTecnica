package com.saldivar.pruebatecnica.activityMain

data class TareaObject (val id:Int, val titulo:String?= null,val descripcion:String?= null, val creacionFecha:String?= null,
val finalizacionFecha:String?= null,val estado:Boolean)