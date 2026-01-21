package com.example.sosphone

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sosphone.databinding.ActivityConfBinding
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
// Importaciones necesarias para DatePicker y tiempo
import android.app.DatePickerDialog
import android.telephony.PhoneNumberUtils
import android.view.View
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Locale

// Definición de la actividad de configuración, hereda de AppCompatActivity.
class ConfActivity : AppCompatActivity() {

    // Variable para el View Binding del layout ActivityConf.
    private lateinit var confBinding: ActivityConfBinding
    // Objeto para manejar las preferencias compartidas (SharedPreferences).
    private lateinit var sharedFich: SharedPreferences
    // Clave para guardar el número de teléfono SOS en SharedPreferences.
    private lateinit var nameSharedPhone: String

    // Nuevas variables para las claves de configuración adicionales.
    // Clave para el delay del Spinner (tiempo entre tiradas de dados).
    private lateinit var nameSharedDelay: String
    // Clave para el idioma de la voz (TTS).
    private lateinit var nameSharedLang: String
    // Clave para la opción de sonido (CheckBox).
    private lateinit var nameSharedSound: String
    // Clave para la fecha de registro (DatePicker).
    private lateinit var nameSharedDate: String

    // Variable para almacenar el timestamp (milisegundos) de la fecha seleccionada.
    private var selectedDate: Long = 0

    // Método llamado al crear la actividad.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout usando View Binding.
        confBinding = ActivityConfBinding.inflate(layoutInflater)
        // Establecer el layout como el contenido de la actividad.
        setContentView(confBinding.root)

        // Inicializar las preferencias compartidas y las claves.
        initPreferentShared()
        // Llamada a la función para configurar la UI y sus listeners.
        setupUIConfiguration()
        // Iniciar la lógica principal de la actividad (comprobar configuración y configurar botón).
        start()
    }

    // Función para inicializar el objeto SharedPreferences y las claves.
    private fun initPreferentShared() {
        // Obtener la clave del nombre del archivo de preferencias desde recursos.
        val nameSharedFich = getString(R.string.name_preferen_shared_fich)
        // Obtener la clave para el teléfono desde recursos.
        this.nameSharedPhone = getString(R.string.name_shared_phone)

        // Definición de las nuevas claves de configuración como literales.
        this.nameSharedDelay = "shared_delay"
        this.nameSharedLang = "shared_lang"
        this.nameSharedSound = "shared_sound"
        this.nameSharedDate = "shared_date"

        // Obtener/crear el archivo de preferencias compartidas.
        this.sharedFich = getSharedPreferences(nameSharedFich, Context.MODE_PRIVATE)
    }

    /**
     * Función que inicializa el estado de los componentes de UI y sus listeners.
     */
    private fun setupUIConfiguration() {
        // 1. Cargar estado previo de los componentes (si existe).
        loadPreviousConfiguration()

        // 2. Configurar el listener para el botón de selección de fecha (DatePicker).
        confBinding.btnSelectDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    /**
     * Carga el estado guardado en SharedPreferences a los componentes de UI.
     */
    private fun loadPreviousConfiguration() {
        // Cargar posición del Spinner Delay (por defecto: 0, el primer elemento).
        val delayPosition = sharedFich.getInt(nameSharedDelay, 0)
        confBinding.spinnerDelay.setSelection(delayPosition)

        // Cargar estado del CheckBox Sound (por defecto: true).
        val soundEnabled = sharedFich.getBoolean(nameSharedSound, true)
        confBinding.checkSound.isChecked = soundEnabled

        // Cargar estado del Radio Language (por defecto: "es").
        val lang = sharedFich.getString(nameSharedLang, "es")
        // Marcar el RadioButton correspondiente al idioma guardado.
        if (lang == "en") confBinding.radioLangEn.isChecked = true else confBinding.radioLangEs.isChecked = true

        // Cargar el timestamp de la fecha (por defecto: fecha y hora actual del sistema).
        selectedDate = sharedFich.getLong(nameSharedDate, System.currentTimeMillis())
        // Actualizar el TextView con la fecha cargada.
        updateDateTextView(selectedDate)
    }


    /**
     * Muestra el DatePickerDialog y actualiza la fecha seleccionada.
     */
    private fun showDatePickerDialog() {
        // Inicializar el Calendar con la fecha previamente seleccionada.
        val calendar = Calendar.getInstance().apply { timeInMillis = selectedDate }

        // Crear y configurar el DatePickerDialog.
        val dateDialog = DatePickerDialog(
            this,
            // Listener que se ejecuta al seleccionar una fecha.
            { _, year, month, dayOfMonth ->
                // Actualizar el objeto Calendar con la fecha seleccionada.
                calendar.set(year, month, dayOfMonth)
                // Guardar el nuevo timestamp.
                selectedDate = calendar.timeInMillis
                // Actualizar el TextView.
                updateDateTextView(selectedDate)
            },
            // Valores iniciales del diálogo (año, mes, día).
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Mostrar el diálogo.
        dateDialog.show()
    }

    /**
     * Actualiza el TextView con la fecha seleccionada en formato legible.
     */
    private fun updateDateTextView(timestamp: Long) {
        // Definir el formato de fecha deseado.
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        // Formatear el timestamp a String.
        val dateString = formatter.format(timestamp)
        // Establecer el texto en el TextView.
        confBinding.txtDateSelected.text = "Fecha de Registro: $dateString"
    }


    // Se ejecuta cada vez que la actividad vuelve a estar en primer plano.
    override fun onResume() {
        super.onResume()
        // Comprobar si se ha vuelto desde MainActivity para cambiar el teléfono.
        val ret = intent.getBooleanExtra("back", false)
        if (ret){
            // Limpiar el campo de texto del teléfono.
            confBinding.editPhone.setText("")
            // Mostrar un mensaje al usuario.
            Toast.makeText(this, R.string.msg_new_phone, Toast.LENGTH_LONG).show()
            // Eliminar el extra para evitar repeticiones si se interrumpe la actividad.
            intent.removeExtra("back")
        }
    }

    // Función principal de inicialización y manejo del botón de configuración.
    private fun start(){
        // Intentar cargar el número de teléfono guardado.
        val sharedPhone : String?  = sharedFich.getString(nameSharedPhone, null)

        // Si ya hay un teléfono configurado, ir directamente a la actividad principal.
        sharedPhone?.let {
            startMainActivity(it)
        }

        // Configurar el listener para el botón de Aceptar/Configurar.
        confBinding.btnConf.setOnClickListener {
            // Obtener el texto del campo de teléfono.
            val numberPhone = confBinding.editPhone.text.toString()
            // 1. Validación de campo vacío.
            if (numberPhone.isEmpty()) {
                Toast.makeText(this, R.string.msg_empty_phone, Toast.LENGTH_LONG).show()
                // 2. Validación de número de teléfono (asumiendo España "ES").
            } else if (!isValidPhoneNumber2(numberPhone, "ES")) {
                Toast.makeText(this, R.string.msg_not_valid_phone, Toast.LENGTH_LONG).show()
            } else {
                // Validación correcta, proceder al proceso de guardado.

                // 1. Mostrar ProgressBar (haciéndolo visible).
                confBinding.progressBarConf.visibility = View.VISIBLE
                // Deshabilitar el botón para evitar múltiples pulsaciones.
                confBinding.btnConf.isEnabled = false

                // 2. Guardar todas las preferencias de configuración.
                saveAllConfiguration()

                // 3. Simular tiempo de espera (creando un nuevo hilo).
                Thread {
                    try {
                        // Dormir el hilo durante 3 segundos (requisito de la tarea).
                        Thread.sleep(3000)
                    } catch (e: InterruptedException) {
                        // Capturar interrupción y reestablecer el estado del hilo.
                        Thread.currentThread().interrupt()
                    }
                    // Volver al hilo principal (UI thread) para actualizar la UI.
                    runOnUiThread {
                        // 4. Ocultar ProgressBar.
                        confBinding.progressBarConf.visibility = View.GONE
                        // Habilitar el botón.
                        confBinding.btnConf.isEnabled = true
                        // Lanzar la actividad principal.
                        startMainActivity(numberPhone)
                    }
                }.start() // Iniciar el nuevo hilo.
            }
        }
    }

    /**
     * Recoge los datos de todos los componentes de UI y los guarda en SharedPreferences.
     */
    private fun saveAllConfiguration() {
        // Obtener el número de teléfono (aunque ya se validó antes, se asegura de usar el texto actual).
        val numberPhone = confBinding.editPhone.text.toString()
        // Obtener un editor para realizar cambios en SharedPreferences.
        val edit = sharedFich.edit()

        // Guardar el EditText Phone.
        edit.putString(nameSharedPhone, numberPhone)

        // Guardar la posición seleccionada del Spinner Delay.
        edit.putInt(nameSharedDelay, confBinding.spinnerDelay.selectedItemPosition)

        // Guardar el estado del CheckBox Sound.
        edit.putBoolean(nameSharedSound, confBinding.checkSound.isChecked)

        // Determinar y guardar el idioma seleccionado en los RadioButtons.
        val langSelected = if (confBinding.radioLangEs.isChecked) "es" else "en"
        edit.putString(nameSharedLang, langSelected)

        // Guardar el timestamp de la fecha seleccionada.
        edit.putLong(nameSharedDate, selectedDate)

        // Aplicar los cambios de forma asíncrona.
        edit.apply()
    }


    // Lanza la actividad principal (MainActivity).
    private fun startMainActivity(phone: String) {
        // Crear un Intent explícito hacia MainActivity.
        val intent = Intent(this@ConfActivity, MainActivity::class.java)
        intent.apply {
            // Pasar el número de teléfono como un extra.
            putExtra(getString(R.string.string_phone), phone)
            // Banderas para limpiar la pila de actividades y evitar duplicados.
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        // Iniciar la actividad.
        startActivity(intent )
    }


    // Función de Android para una validación básica de teléfono (no usada, solo como alternativa).
    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return PhoneNumberUtils.isGlobalPhoneNumber(phoneNumber)
    }

    // Función de validación de teléfono más robusta usando libphonenumber de Google.
    fun isValidPhoneNumber2(phoneNumber: String, countryCode: String): Boolean {
        // Obtener la instancia del validador.
        val phoneUtil = PhoneNumberUtil.getInstance()
        return try {
            // Parsear el número con el código de país (ej. "ES").
            val number = phoneUtil.parse(phoneNumber, countryCode)
            // Verificar si el número parseado es válido.
            phoneUtil.isValidNumber(number)
        } catch (e: NumberParseException) {
            // Capturar errores de parseo (ej. formato incorrecto).
            e.printStackTrace()
            false
        }
    }

    // Se llama cuando la actividad recibe un nuevo Intent (útil con las banderas CLEAR_TOP/SINGLE_TOP).
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Reemplazar el Intent actual con el nuevo Intent.
        setIntent(intent)
    }
}