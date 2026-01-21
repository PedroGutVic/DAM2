package com.example.sosphone // Define el paquete de la aplicación.

import android.os.Bundle // Importa Bundle para guardar el estado de la Activity.
import android.speech.tts.TextToSpeech // Importa la clase principal para la síntesis de voz (Text-To-Speech).
import android.util.Log // Importa Log para mensajes de depuración/error.
import android.widget.Toast // Importa Toast para mostrar mensajes cortos al usuario.
import androidx.appcompat.app.AppCompatActivity // Importa la clase base de la Activity.
import com.example.sosphone.databinding.ActivityChistesBinding // Importa la clase de Binding para acceder a las vistas del layout.
import java.util.Locale // Importa Locale para configurar el idioma de la voz.

// La clase TTSActivity es una Activity que implementa TextToSpeech.OnInitListener
// para recibir una notificación cuando el motor TTS esté listo.
class TTSActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    // Variable para acceder a las vistas del layout 'activity_chistes.xml' usando View Binding.
    private lateinit var ttsBinding: ActivityChistesBinding
    // Instancia del motor TextToSpeech, puede ser nula hasta que se inicialice.
    private var tts: TextToSpeech? = null
    // Variable para almacenar el idioma de la voz, por defecto 'es' (español).
    private var voiceLanguage: String = "es"

    // Lista inmutable de chistes que se utilizarán para la síntesis de voz.
    private val jokes = listOf(
        "¿Qué hace una abeja en el gimnasio? ¡Zum-ba!",
        "¿Cuál es el último mono? El que dice 'A mí plin'.",
        "Había una vez un perro llamado Chiste. Se cayó y se mató.",
        "Un semáforo le dice a otro: 'No me mires que me pongo rojo'.",
        "¿Qué le dice un jardinero a otro? Nos vemos cuando podamos.",
        "¿Qué hace una taza en el suelo? Una indecisión.",
        "El mundo es un pañuelo. ¡Y nosotros unos mocos!",
        "Si la vida te da limones, haz limonada y véndela. ¡Emprendedor!",
        "Le dice un pez a otro: '¿Qué hace tu mamá? ¡Nada!'",
        "¿Sabes por qué los informáticos confunden Halloween con Navidad? Porque Oct 31 es igual a Dec 25."
    )

    // Se llama cuando la actividad es creada.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout usando View Binding y establecerlo como el contenido de la Activity.
        ttsBinding = ActivityChistesBinding.inflate(layoutInflater)
        setContentView(ttsBinding.root)

        // 1. OBTENER LA CONFIGURACIÓN DE IDIOMA
        // Obtiene el idioma ("es" o "en") pasado desde la Activity anterior (MainActivity) a través del Intent.
        // Si no se encuentra el extra, usa "es" por defecto.
        voiceLanguage = intent.getStringExtra("VOICE_LANG") ?: "es"

        // Muestra el idioma configurado en un TextView.
        val langName = if (voiceLanguage == "es") "Español" else "Inglés"
        ttsBinding.txtLangInfo.text = "Idioma de voz configurado: $langName"

        // 2. Inicializar el motor TTS. El segundo argumento 'this' (la Activity) es el OnInitListener.
        // El idioma y la configuración real se aplicarán en el callback onInit.
        tts = TextToSpeech(this, this)

        // 3. Listener para el botón de chiste aleatorio.
        ttsBinding.btnRandomJoke.setOnClickListener {
            // Selecciona un chiste al azar de la lista.
            val randomJoke = jokes.random()
            // Muestra el chiste en el TextView.
            ttsBinding.txtJokeDisplay.text = randomJoke
            // Llama a la función para que el motor TTS lo hable.
            speak(randomJoke)
        }

        // 4. Listener para el botón de escuchar todos los chistes en serie.
        ttsBinding.btnAllJokes.setOnClickListener {
            speakAllJokes()
        }
    }

    /**
     * Se llama cuando el motor TextToSpeech está inicializado y listo para usarse.
     * Este es un método obligatorio de la interfaz TextToSpeech.OnInitListener.
     */
    override fun onInit(status: Int) {
        // Verifica si la inicialización fue exitosa.
        if (status == TextToSpeech.SUCCESS) {
            // Crear el objeto Locale basado en la configuración de idioma obtenida.
            // Para español, usa un constructor de Locale más específico ("spa", "ESP").
            val locale = if (voiceLanguage == "es") Locale("spa", "ESP") else Locale.US
            // Intenta establecer el idioma del motor TTS.
            val result = tts!!.setLanguage(locale)

            // Comprueba si el idioma no es soportado o faltan datos.
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Log de error y notificar al usuario.
                Log.e("TTS", "El idioma ($voiceLanguage) no es soportado o faltan datos.")
                Toast.makeText(this, "Idioma no soportado. Usando el idioma por defecto.", Toast.LENGTH_LONG).show()
                // Si falla, usa el idioma por defecto del dispositivo.
                tts!!.language = Locale.getDefault()
            }
        } else {
            // Log de error si la inicialización falla.
            Log.e("TTS", "Inicialización de TTS fallida.")
            Toast.makeText(this, "Error al inicializar el motor de voz.", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Habla un texto, limpiando la cola anterior (QUEUE_FLUSH).
     * @param text El texto a ser hablado.
     */
    private fun speak(text: String) {
        // QUEUE_FLUSH: borra cualquier cosa que se esté hablando o esté en cola y reproduce el nuevo texto inmediatamente.
        // El último parámetro es el utteranceId para identificar la solicitud.
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "TTS_CHISTE_RANDOM")
    }

    /**
     * Reproduce los 10 chistes en secuencia.
     */
    private fun speakAllJokes() {
        // Actualiza el TextView para indicar que se inicia la reproducción en serie.
        ttsBinding.txtJokeDisplay.text = "Reproduciendo los 10 chistes en serie..."

        // Detiene cualquier proceso de voz actual.
        tts?.stop()

        // Itera sobre la lista de chistes.
        for (joke in jokes) {
            // Añade el chiste a la cola (QUEUE_ADD). Se hablará después de que termine la emisión anterior.
            tts?.speak(joke, TextToSpeech.QUEUE_ADD, null, joke)
            // Añade una pausa silente de 500ms (0.5 segundos) a la cola entre chistes.
            tts?.playSilentUtterance(500, TextToSpeech.QUEUE_ADD, "pausa")
        }
    }

    // Se llama antes de que la actividad sea destruida.
    override fun onDestroy() {
        // Si el motor TTS existe, se debe detener y liberar sus recursos.
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown() // Libera los recursos nativos.
        }
        // Llama a la implementación de la superclase.
        super.onDestroy()
    }
}