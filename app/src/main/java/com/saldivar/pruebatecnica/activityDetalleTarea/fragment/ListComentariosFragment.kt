package com.saldivar.pruebatecnica.activityDetalleTarea.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.activityDetalleTarea.*
import com.saldivar.pruebatecnica.activityMain.TareaObject
import com.saldivar.pruebatecnica.activityMain.TareasAdapter
import com.saldivar.pruebatecnica.activityMain.fragment.*
import com.saldivar.pruebatecnica.db.Comentarios
import com.saldivar.pruebatecnica.db.Tareas
import kotlinx.android.synthetic.main.activity_detalle_tarea.*
import kotlinx.android.synthetic.main.fragment_list_comentarios.view.*
import kotlinx.android.synthetic.main.fragment_list_tareas.view.*


class ListComentariosFragment : Fragment(),ListComentariosFragmentViewInterface {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootview =inflater.inflate(R.layout.fragment_list_comentarios, container, false)
        presenter = ListComentariosFragmentPresenter(this)
        recycler = rootview.recycler_comentarios as RecyclerView
        consultar(context!!)
        return rootview
    }

    private fun consultar(context: Context) {
        presenter.getAllComentarios(context,DatosTareaElegidaDetalle.id)
    }

    companion object {
        private lateinit var presenter: ListComentariosFragmentPresenterInterface
        private lateinit var recycler: RecyclerView
        private lateinit var adapter: ComentariosAdapter
        fun newInstance(): ListComentariosFragment = ListComentariosFragment()
    }

    override fun comentariosObtenidos(listaComentarios: List<Comentarios>) {
        if(listaComentarios.isNotEmpty()){
            val tareaObject = MapperListComentarios().listComentarios(listaComentarios)
            setRecyclerView(tareaObject)
        }
        else{
            insertDatosDefecto()
            consultar(context!!)
        }
    }

    private fun setRecyclerView(datosComentario: MutableList<ComentarioObject>) {
        this.activity!!.numeroComentarios.text = "Comentarios: (${datosComentario.size})"
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(context)
        adapter =(ComentariosAdapter(
            datosComentario,
            object : RecyclerComentariosListener{
                override fun onClick(flight: TareaObject, position: Int) {

                }
            }
        ))
        recycler.adapter = adapter
    }

    private fun insertDatosDefecto() {
        val listObject = mutableListOf<Comentarios>()
        val objectVal0 = Comentarios(0,DatosTareaElegidaDetalle.id,"anonimus",
            "Otra vez soy yo")
        listObject.add(0,objectVal0)
        val objectVal1 = Comentarios(0,DatosTareaElegidaDetalle.id,"anonimus",
                "Necesito saber más sobre la tarea. La verdad  no entiendo mucho lo que dice." +
                        "Podría actualizar su contenido por favor. Sería de gran utilidad")
        listObject.add(1,objectVal1)
        insertarBD(listObject,context!!)
    }

    private fun insertarBD(listObject: MutableList<Comentarios>, context: Context) {
        presenter.insertarDatosDefecto(context,listObject)
    }
}