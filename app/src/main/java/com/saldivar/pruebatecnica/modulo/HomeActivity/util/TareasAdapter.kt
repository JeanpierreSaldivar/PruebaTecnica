package com.saldivar.pruebatecnica.modulo.HomeActivity.util

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.helper.inflate
import kotlinx.android.synthetic.main.item_recyler_tareas.view.*


class TareasAdapter(private val listener: RecyclerTareasListener)
    : RecyclerView.Adapter<TareasAdapter.MainViewHolder>()  {
    var flight = mutableListOf<Tareas>()
    fun setDataList(data: MutableList<Tareas>){
         flight = data
    }
    fun addItem(position: Int,tarea: Tareas) {
        flight.add(position,tarea)
        notifyItemInserted(position)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=(MainViewHolder(
        parent.inflate(
            R.layout.item_recyler_tareas
        )
    )
            )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int)= holder.bin(
        flight[position],
        listener
    )

    override fun getItemCount() = flight.size

    class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")
        fun bin(tarea: Tareas, listener: RecyclerTareasListener)= with(itemView){
            if(tarea.id<10){
                ordenNumero.text = "${"0" + tarea.id.toString()}"
            }else{
                ordenNumero.text = "${tarea.id}"
            }
            titulo.text = tarea.titulo
            descripcion.text = tarea.descripcion
            creacion.text = "Creación: ${tarea.creacion}"
            finalizacion.text = "Finalizado: ${tarea.finalizacion}"
            if(tarea.estado){
                estado.isChecked = true
            }
            item_tarea.setOnClickListener{listener.onClick(tarea, adapterPosition)}
            estado.setOnCheckedChangeListener { _, _ ->
                listener.change(tarea, adapterPosition)
            }
        }
    }


}