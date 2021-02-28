package com.saldivar.pruebatecnica.modulo.HomeActivity.View

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.saldivar.pruebatecnica.MyAplicationClass
import com.saldivar.pruebatecnica.R
import com.saldivar.pruebatecnica.helper.DatosTareaElegidaDetalle
import com.saldivar.pruebatecnica.modulo.HomeActivity.util.TareasAdapter
import com.saldivar.pruebatecnica.db.Tareas
import com.saldivar.pruebatecnica.helper.showDialog
import com.saldivar.pruebatecnica.modulo.HomeActivity.mvp.HomeMVP
import com.saldivar.pruebatecnica.modulo.HomeActivity.presenter.PresenterHomeActivity
import com.saldivar.pruebatecnica.modulo.ListTareasFragmentViewInterface
import com.saldivar.pruebatecnica.modulo.detalleTarea.DetalleTareaActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_list_tareas.view.*


class ListTareasFragment : Fragment(),View.OnClickListener,HomeMVP.View {
    private lateinit var presenter: PresenterHomeActivity
    private lateinit var recycler: RecyclerView
    private lateinit var visualizadorOjo: ImageView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.fragment_list_tareas, container, false)
        visualizadorOjo = activity!!.findViewById(R.id.visualizador)
        rootview.flotanButton.setOnClickListener(this)
        activity!!.visualizador.setOnClickListener(this)
        presenter = PresenterHomeActivity(this)
        recycler = rootview.recycler_tareas as RecyclerView
        consultar()
        return rootview
    }
    companion object {
        fun newInstance(): ListTareasFragment = ListTareasFragment()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.visualizador->{
                visualizadorOjo.background = presenter.cambiarEstadoOjo()
                consultar()
            }
            R.id.flotanButton -> {
                showDialog(dialog(), this.activity!!)
            }
        }
    }

    internal fun consultar() {
        presenter.consultarListTareas(recycler)
    }


    private fun dialog() = LayoutInflater.from(MyAplicationClass.ctx).
    inflate(R.layout.alert_dialog_nueva_tarea, this.activity?.findViewById(R.id.alertNuevaTarea))

    override fun nextActivity() {
        val intent = Intent(context, DetalleTareaActivity::class.java)
        context!!.startActivity(intent)
        (context as Activity).overridePendingTransition(
            R.anim.left_in, R.anim.left_out
        )
    }

    override fun showToask(message: String) {
        Toast.makeText(MyAplicationClass.ctx, message, Toast.LENGTH_SHORT).show()}

}