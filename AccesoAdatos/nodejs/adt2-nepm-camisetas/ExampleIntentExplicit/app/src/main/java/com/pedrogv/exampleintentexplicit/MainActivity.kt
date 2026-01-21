package com.pedrogv.exampleintentexplicit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnFirst: Button
    private lateinit var btnSecond : Button
    private lateinit var intent: Intent

    private fun initEvent(){
        btnFirst = findViewById(R.id.button_1)
        btnSecond = findViewById(R.id.button_2)

        btnFirst.setOnClickListener {view ->
        intent = Intent (this, FirstActivityIntentExplicit::class.java)
            .apply { putExtra("name", "Pedro") }
        startActivity(intent)
        }

        btnSecond.setOnClickListener {view ->
            intent = Intent (this, SecondActivityIntentExplicit::class.java)
                .apply { putExtra("name", "Pedro2") }
            startActivity(intent)

        }

}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //enableEdgeToEdge()
        /*
        * En R.Layout.Activity_main , encontramos el arbol de elementos visuales de ete activity .
        * Lo que hace el setContentView , es coger todo ese arbol de elementos xml
        *
        * */
        setContentView(R.layout.activity_main)

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
}