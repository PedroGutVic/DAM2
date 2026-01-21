// Asegúrate de que este paquete sea el de tu aplicación principal (e.g., com.example.sosphone)
package com.example.sosphone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.sosphone.databinding.ActivityDiceBinding // **Asegura que tu binding se llame así**
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random
import android.media.MediaPlayer // Añadido para gestionar el sonido

// Clase renombrada para ser la Activity secundaria de Dados
class DiceActivity : AppCompatActivity() {

    private lateinit var bindingDice : ActivityDiceBinding // Referencia al binding de la nueva Activity
    private var sum : Int = 0

    private var mediaPlayer: MediaPlayer? = null // Para el sonido del dado

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingDice = ActivityDiceBinding.inflate(layoutInflater)
        setContentView(bindingDice.root)

        initEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun initEvent() {
        bindingDice.txtResultado.visibility = View.INVISIBLE
        bindingDice.imageButton.setOnClickListener{
            bindingDice.txtResultado.visibility = View.VISIBLE
            game()
        }
    }

    private fun game(){
        if (comprobar() == true) {
            sheduleRun()
        }
    }

    private fun sheduleRun() {

        val schedulerExecutor = Executors.newSingleThreadScheduledExecutor()
        // Usa el retardo configurable (tiempo entre cada tirada visual)
        val intervalMs = 1000

        for (i in 1..5){//lanzamos 5 veces el dado (tiradas visuales)
            schedulerExecutor.schedule(
                {
                    throwDadoInTime()
                },
                // La i.toLong() * intervalMs asegura que cada tirada se ejecute después del intervalo
                intervalMs * i.toLong(), TimeUnit.MILLISECONDS)
        }

        schedulerExecutor.schedule({//El último hilo, es mostrar el resultado.
            runOnUiThread {
                viewResult()
                findNumber()
            }
        },
            // Se ejecuta después de la última tirada (5 * intervalMs) + un intervalo adicional
            intervalMs * 6.toLong(), TimeUnit.MILLISECONDS)

        schedulerExecutor.shutdown()
    }


    private fun throwDadoInTime() {
        // Corregido el límite superior de Random.nextInt() a 7 (exclusivo)
        val numDados = Array(3){Random.nextInt(1, 7)}
        val imagViews : Array<ImageView> = arrayOf(
            bindingDice.imagviewDado1,
            bindingDice.imagviewDado2,
            bindingDice.imagviewDado3)

        sum = numDados.sum()
        // El bucle debe ir de 0 a 2 para los 3 dados
        for (i in 0..2)
            selectView(imagViews[i], numDados[i])
    }


    private fun selectView(imgV: ImageView, v: Int) {
        when (v){
            1 -> imgV.setImageResource(R.drawable.dado1);
            2 -> imgV.setImageResource(R.drawable.dado2);
            3 -> imgV.setImageResource(R.drawable.dado3);
            4 -> imgV.setImageResource(R.drawable.dado4);
            5 -> imgV.setImageResource(R.drawable.dado5);
            6 -> imgV.setImageResource(R.drawable.dado6);
        }

    }


    private fun viewResult() {
        bindingDice.txtResultado.text = sum.toString()
    }


    private fun findNumber(): Int? {
        val textEdit = bindingDice.NumUser.text.toString()
        val numeroUsuario = textEdit.toIntOrNull()

        // Si acierta
        if (sum == numeroUsuario) {
            AlertDialog.Builder(this)
                .setTitle("Ganador")
                .setMessage("Has acertado.")
                .setPositiveButton("OK", null)
                .show()
        }
        // Si falla
        if (sum != numeroUsuario){
            AlertDialog.Builder(this)
                .setTitle("Perdedor")
                .setMessage("Has Fallado.")
                .setPositiveButton("OK", null)
                .show()
        }
        return numeroUsuario
    }

    private fun comprobar(): Boolean?{
        val textEdit = bindingDice.NumUser.text.toString()
        val numeroUsuario = textEdit.toIntOrNull()

        // Lógica de validación se mantiene igual
        if (textEdit.isEmpty()) {
            AlertDialog.Builder(this)
                .setTitle("Número requerido")
                .setMessage("Por favor, introduce un número antes de tirar los dados.")
                .setPositiveButton("OK", null)
                .show()
            return false
        }
        if (numeroUsuario == null) {
            AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Debes introducir un número válido.")
                .setPositiveButton("OK", null)
                .show()
            return false
        }
        if (numeroUsuario !in 3..18) {
            AlertDialog.Builder(this)
                .setTitle("El número debe estar entre 3 y 18")
                .setMessage("Por favor, introduce un número válido.")
                .setPositiveButton("OK", null)
                .show()
            return false
        }
        return true
    }
}