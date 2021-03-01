package com.saldivar.pruebatecnica.modulo.HomeActivity.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.saldivar.pruebatecnica.R

class HomeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()
        openFragment(ListTareasFragment.newInstance())
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_tareas,fragment)
            addToBackStack(null)
            commit() }}


    override fun onBackPressed() {
        finish()
    }
}