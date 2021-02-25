package com.saldivar.pruebatecnica.activityMain.fragment

import com.saldivar.pruebatecnica.activityMain.TareaObject

interface RecyclerTareasListener {
    fun onClick(flight: TareaObject, position :Int)
    fun onDelete(flight: TareaObject, position: Int)
    fun change(flight: TareaObject, position: Int)
}
