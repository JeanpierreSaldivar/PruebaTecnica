package com.saldivar.pruebatecnica.activityMain

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.activityMain.fragment.RecyclerTareasListener
import com.saldivar.pruebatecnica.inflate
import kotlinx.android.synthetic.main.item_recyler_tareas.view.*

class TareasAdapter(private  val flight:List<TareaObject>, private  val listener: RecyclerTareasListener)
    : RecyclerView.Adapter<TareasAdapter.MainViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=(MainViewHolder(parent.inflate(R.layout.item_recyler_tareas))
            )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int)= holder.bin(flight[position],listener)

    override fun getItemCount() = flight.size

    class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")
        fun bin(tarea: TareaObject, listener: RecyclerTareasListener)= with(itemView){
            ordenNumero.text = "${"0" + tarea.id.toString() } "
            titulo.text = tarea.titulo
            descripcion.text = tarea.descripcion
            creacion.text = "Creación: ${tarea.creacionFecha}"
            finalizacion.text = "Finalizado: ${tarea.finalizacionFecha}"
            if(tarea.estado){
                estado.isChecked = true
            }
            item_tarea.setOnClickListener{listener.onClick(tarea,adapterPosition)}
            estado.setOnCheckedChangeListener { _, _ ->
                listener.change(tarea, adapterPosition,context)
            }
            /*item_certificacion.setOnClickListener { listener.onClick(certificacion,adapterPosition) */
        }
    }

}