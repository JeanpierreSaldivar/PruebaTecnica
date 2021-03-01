package com.saldivar.pruebatecnica.helper

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.db.Comentarios
import kotlinx.android.synthetic.main.item_recycler_comentarios.view.*

class ComentariosAdapter(private  val flight:List<Comentarios>)
    : RecyclerView.Adapter<ComentariosAdapter.MainViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=(MainViewHolder(
        parent.inflate(R.layout.item_recycler_comentarios)
    ))

    override fun onBindViewHolder(holder: MainViewHolder, position: Int)= holder.bin(flight[position])

    override fun getItemCount()= flight.size

    class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")
        fun bin(comentario: Comentarios)= with(itemView){
            comentario_user.text =comentario.comentario
        }
    }
}