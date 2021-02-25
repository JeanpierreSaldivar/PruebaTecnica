package com.saldivar.pruebatecnica.activityDetalleTarea

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.inflate
import kotlinx.android.synthetic.main.item_recycler_comentarios.view.*

class ComentariosAdapter(private  val flight:List<ComentarioObject>, private  val listener: RecyclerComentariosListener)
    : RecyclerView.Adapter<ComentariosAdapter.MainViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=(ComentariosAdapter.MainViewHolder(
        parent.inflate(R.layout.item_recycler_comentarios)
    ))

    override fun onBindViewHolder(holder: MainViewHolder, position: Int)= holder.bin(flight[position],listener)

    override fun getItemCount()= flight.size

    class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")
        fun bin(comentario: ComentarioObject, listener: RecyclerComentariosListener)= with(itemView){
            comentario_user.text =comentario.comentario
        }
    }
}