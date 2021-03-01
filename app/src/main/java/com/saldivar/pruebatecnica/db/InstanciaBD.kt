package com.saldivar.pruebatecnica.db

import com.saldivar.pruebatecnica.helper.MyAplicationClass

object InstanciaBD {
    val dbRoom =RoomDB.getDataBase(MyAplicationClass.ctx!!).roomDAO()
}