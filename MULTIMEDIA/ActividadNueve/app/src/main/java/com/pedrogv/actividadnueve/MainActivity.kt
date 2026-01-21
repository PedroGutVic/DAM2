package com.example.intentsexplicitos

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var textResultado: TextView? = null
    private var btnAbrirActivity2: Button? = null

    @Override
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResultado = findViewById(R.id.textResultado)
        btnAbrirActivity2 = findViewById(R.id.btnAbrirActivity2)

        btnAbrirActivity2.setOnClickListener({ v ->
            // Creamos el intent explícito
            val i: Intent = Intent(this@MainActivity, Activity2::class.java)
            // Llamamos al Activity2 esperando un resultado
            startActivityForResult(
                i,
                com.example.intentsexplicitos.MainActivity.Companion.COD_ACTI2
            )
        })
    }

    // Este método se ejecuta automáticamente al volver de Activity2
    @Override
    protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == com.example.intentsexplicitos.MainActivity.Companion.COD_ACTI2) {
            // El resultCode es el número de clicks
            textResultado.setText("Has pulsado " + resultCode + " veces")
        }
    }

    companion object {
        private const val COD_ACTI2 = 2 // Código para identificar Activity2
    }
}