package com.example.sosphone

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sosphone.databinding.ActivityPpalBinding
import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.provider.AlarmClock
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.sosphone.databinding.ActivityChistesBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var mainBinding : ActivityPpalBinding
    private  var phoneSOS : String? = null
    //Acepta como lanzador un string que representa el permiso a solicitar.
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var permisionPhone = false
    private var permisionGoogle = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityPpalBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(mainBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
        initEventCall()
        initEventLink()
        initEventAlarm()
        initEventMio()
        initDados()
        initChistes()
    }

    override fun onResume() {
        super.onResume()
        permisionPhone = isPermissionCall()
        val stringPhone  = getString(R.string.string_phone)
        phoneSOS = intent.getStringExtra(stringPhone)
        mainBinding.txtPhone.setText(phoneSOS)


    }



    private fun init(){
        registerLauncher()
        if (!isPermissionCall()) //verificamos permisos en de llamada.
            requestPermissionLauncher.launch(Manifest.permission. CALL_PHONE)

        mainBinding.ivChangePhone.setOnClickListener {
            val nameSharedFich = getString(R.string.name_preferen_shared_fich)
            val nameSharedPhone = getString(R.string.name_shared_phone)
            val sharedFich = getSharedPreferences(nameSharedFich, Context.MODE_PRIVATE)
            val edit = sharedFich.edit()
            edit.remove(nameSharedPhone)
            edit.apply()
            val intent = Intent(this, ConfActivity::class.java )
                .apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    putExtra("back", true)//volvemos desde El ACtivity2
                }
            startActivity(intent)
        }

    }


    private fun registerLauncher(){
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { //lambda
            /*
            Se ejecuta esta lambda, cuando el usuario pulsa aceptar/denegar
             */
                isGranted->
            if (isGranted) {
                permisionPhone = true
            }
            else {
                Toast.makeText( this, "Necesitas habilitar los permisos",
                    Toast.LENGTH_LONG).show()
                goToConfiguracionApp()  //abrimos la configuración de la aplicación.
            }
        }
    }


    //inicia el proceso de llamada, junto con la petición de permisos.
    private fun initEventCall() {
        mainBinding.buttonCall.setOnClickListener {
            permisionPhone=isPermissionCall()
            if (permisionPhone)
                call()
            else
                requestPermissionLauncher.launch(Manifest.permission. CALL_PHONE)
        }
    }

    private fun initEventAlarm(){
        mainBinding.buttonAlarm.setOnClickListener {
            alarm()
        }
    }

    private fun initEventLink(){
        mainBinding.buttonLink.setOnClickListener {
            link()
        }
    }

    private fun initEventMio(){
        mainBinding.buttonMio.setOnClickListener {
        mio()
        }
    }

    private fun initDados(){
        mainBinding.buttonDice.setOnClickListener {
            dice()
        }
    }

    private fun initChistes(){
        mainBinding.buttonTTS.setOnClickListener {
            Chistes()
        }
    }

    private fun isPermissionCall():Boolean{
        //Para versión del sdk inferior a la API 23, no hace falta pedir permisos en t. ejecución.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true  //no hace falta pedir permisos en t. real al usuario
        else
            return isPermissionToCall() //Hay que ver si se concedieron en ejecución o no.
    }


    private fun isPermissionToCall() = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED

    private fun call() {
        val intent = Intent(Intent.ACTION_CALL).apply {  //creamos la intención
            //Indicamos la Uri que es la forma de indicarle a Android que es un teléfono.
            data = Uri.parse("tel:"+phoneSOS!!)
        }
        startActivity(intent)
    }

    private fun link(){
        val url = "https://gitlab.iesvirgendelcarmen.com/PedroGV"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)

    }
    private fun alarm() {
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MINUTE, 2) // Añade 2 minutos al tiempo actual
        }

        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_HOUR, calendar.get(Calendar.HOUR_OF_DAY))
            putExtra(AlarmClock.EXTRA_MINUTES, calendar.get(Calendar.MINUTE))
            putExtra(AlarmClock.EXTRA_MESSAGE, "Alarma SOS")
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No hay una app de alarma disponible", Toast.LENGTH_SHORT).show()
        }
    }


    private fun mio() {
        val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, "No hay aplicación de cámara disponible", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dice(){
        val intent = Intent(this, DiceActivity::class.java)
        startActivity(intent)
    }
    // En MainActivity.kt
    private fun Chistes(){
        // 1. CORRECCIÓN: Usa TTSActivity::class.java, que es la Activity real.
        val intent = Intent(this, TTSActivity::class.java)
        intent.putExtra("VOICE_LANG", "es")

        startActivity(intent)
    }


    private fun goToConfiguracionApp(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }

        startActivity(intent)
    }


    //Main
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent) // Actualiza el Intent con los nuevos extras
    }

}
