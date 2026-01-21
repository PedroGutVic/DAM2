package com.pedrogv.intent_explcitos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val COD_ACTI2 = 2
    }

    private var contador = 0
    private lateinit var btnAbrir: Button
    private lateinit var txtResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAbrir = findViewById(R.id.btnAbrir)
        txtResultado = findViewById(R.id.txtResultado)

        btnAbrir.setOnClickListener {
            // Creamos el Intent y enviamos el contador actual
            val intent = Intent(this, Activity2::class.java)
            intent.putExtra("contador", contador)
            startActivityForResult(intent, COD_ACTI2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == COD_ACTI2 && resultCode == RESULT_OK) {
            contador = data?.getIntExtra("contador", 0) ?: 0
            txtResultado.text = "Contador actual: $contador"
        }
    }
}
