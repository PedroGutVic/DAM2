package com.example.srodenas.interfazelementosentrada.porsi_no_os_enterais

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.srodenas.interfazelementosentrada.MainActivity

class ClickOnButton (val context: Context):  View.OnClickListener{
    override fun onClick(v: View?) {
        Toast.makeText(context as MainActivity," Véis el porqué necesito lambda o anónimas??",
            Toast.LENGTH_LONG).show()
    }
}