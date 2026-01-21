package com.example.srodenas.interfazelementosentrada.porsi_no_os_enterais

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.example.srodenas.interfazelementosentrada.MainActivity

abstract class SelectElementSpinnerAbstract (val context : Context) : AdapterView.OnItemSelectedListener{
    lateinit var element : String

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}