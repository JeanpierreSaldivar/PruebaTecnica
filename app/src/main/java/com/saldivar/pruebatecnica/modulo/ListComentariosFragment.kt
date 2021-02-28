package com.saldivar.pruebatecnica.modulo

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.helper.*
import com.saldivar.pruebatecnica.modulo.HomeActivity.mvp.HomeMVP
import com.saldivar.pruebatecnica.modulo.HomeActivity.presenter.PresenterHomeActivity
import com.saldivar.pruebatecnica.modulo.detalleTarea.DetalleTareaActivityPresenter
import com.saldivar.pruebatecnica.modulo.detalleTarea.DetalleTareaActivityPresenterInterface
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_detalle_tarea.*
import kotlinx.android.synthetic.main.fragment_list_comentarios.view.*


class ListComentariosFragment : Fragment(), ListComentariosFragmentViewInterface {
    private lateinit var presenter: ListComentariosFragmentPresenterInterface
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ComentariosAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview =inflater.inflate(R.layout.fragment_list_comentarios, container, false)
        presenter = ListComentariosFragmentPresenter(this)
        recycler = rootview.findViewById(R.id.recycler_comentarios)
        consultar()
        return rootview
    }

    fun consultar() {
       /* presenter.getAllComentarios( DatosTareaElegidaDetalle.id)*/
    }

    companion object {
        fun newInstance(): ListComentariosFragment = ListComentariosFragment()
    }

    override fun comentariosObtenidos(listaComentarios: List<Comentarios>) {
        if(listaComentarios.isNotEmpty()){
            val tareaObject = listaComentarios
            setRecyclerView(tareaObject)
        }
        else{
            insertDatosDefecto()
           /* consultar(context!!)*/
        }
    }

    private fun setRecyclerView(datosComentario: List<Comentarios>) {
        this.activity!!.numeroComentarios.text = "Comentarios: (${datosComentario.size})"
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(context)
        adapter =(ComentariosAdapter(
            datosComentario,
            object : RecyclerComentariosListener {
                override fun onClick(flight: Tareas, position: Int) {

                }
            }
        ))
        recycler.adapter = adapter
    }

    private fun insertDatosDefecto() {
        val listObject = mutableListOf<Comentarios>()
        val objectVal0 = Comentarios(0, DatosTareaElegidaDetalle.id,"anonimus",
            "Otra vez soy yo")
        listObject.add(0,objectVal0)
        val objectVal1 = Comentarios(0, DatosTareaElegidaDetalle.id,"anonimus",
                "Necesito saber más sobre la tarea. La verdad  no entiendo mucho lo que dice." +
                        "Podría actualizar su contenido por favor. Sería de gran utilidad")
        listObject.add(1,objectVal1)
        insertarBD(listObject,context!!)
    }

    private fun insertarBD(listObject: MutableList<Comentarios>, context: Context) {
        presenter.insertarDatosDefecto(context,listObject)
    }
}