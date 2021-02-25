package com.saldivar.pruebatecnica

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog

fun ViewGroup.inflate(layoutId:Int)= LayoutInflater.from(context).inflate(layoutId,this,false)!!

fun showDialog(mDialogView: View, context: Context) {
    val mBuilder = AlertDialog.Builder(context).setView(mDialogView)
    val  mAlertDialog = mBuilder.show()
    mAlertDialog.window?.setBackgroundDrawable(null)
    /*val body = mDialogView.findViewById(R.id.texto) as TextView
    body.text = message*/
}