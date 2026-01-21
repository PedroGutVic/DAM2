package com.pedrogv.intent_explcitos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Activity2 : AppCompatActivity() {

    private var contador = 0
    private lateinit var btnClick: Button
    private lateinit var btnCerrar: Button
    private lateinit var txtContador: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btnClick = findViewById(R.id.btnClick)
        btnCerrar = findViewById(R.id.btnCerrar)
        txtContador = findViewById(R.id.txtContador)

        // Recuperamos el contador del Activity1
        contador = intent.getIntExtra("contador", 0)
        mostrarContador()

        btnClick.setOnClickListener {
            contador++
            mostrarContador()
        }

        btnCerrar.setOnClickListener {
            val data = Intent()
            data.putExtra("contador", contador)
            setResult(RESULT_OK, data)
            finish()
        }
    }

    private fun mostrarContador() {
        txtContador.text = "Valor del contador: $contador"
    }
}
