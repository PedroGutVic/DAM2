package com.example.srodenas.interfazelementosentrada.porsi_no_os_enterais

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.srodenas.interfazelementosentrada.MainActivity

class SelectElementSpinner(val context: Context) : AdapterView.OnItemSelectedListener {
  lateinit var element : String
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        element = parent?.getItemAtPosition(position).toString()
        Toast.makeText(context as MainActivity, element+"!!", Toast.LENGTH_LONG).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(context as MainActivity, "No selecciono nada", Toast.LENGTH_LONG).show()

    }
}