📱 CallPhoneSOS

Aplicación móvil Android desarrollada en Kotlin que permite realizar llamadas de emergencia de manera rápida y sencilla.

🧭 Descripción general

CallPhoneSOS es una aplicación enfocada en ofrecer una respuesta inmediata ante situaciones de emergencia.
Su función principal es permitir al usuario realizar una llamada directa a un número de contacto predefinido con tan solo un toque.

Además, la aplicación gestiona permisos de llamadas, muestra notificaciones y puede extenderse fácilmente para enviar mensajes de alerta o compartir ubicación, manteniendo un diseño simple y optimizado.

⚙️ Estructura del proyecto

El proyecto sigue la estructura típica de una app Android moderna:
```
CallPhoneSOS/
 ├─ app/
 │   ├─ src/
 │   │   ├─ main/
 │   │   │   ├─ AndroidManifest.xml     # Configuración general de la app
 │   │   │   ├─ java/...                # Código fuente (Kotlin)
 │   │   │   └─ res/...                 # Recursos: layouts, strings, íconos, etc.
 │   ├─ build.gradle.kts                # Configuración del módulo app
 ├─ build.gradle.kts                    # Configuración global del proyecto
 ├─ settings.gradle.kts                 # Definición de módulos
 └─ gradle.properties                   # Propiedades del entorno
```
🧩 Funcionamiento del código
🏁 1. MainActivity.kt

El punto de entrada principal de la aplicación.
En esta clase se define la interfaz principal y la acción del botón de emergencia.

Funciones clave:

onCreate(): Inicializa la vista y los componentes.

checkCallPermission(): Verifica si el usuario ha concedido el permiso para realizar llamadas.

makeEmergencyCall(): Ejecuta la llamada al número de emergencia configurado mediante un Intent de tipo ACTION_CALL.

Ejemplo simplificado:

val phoneNumber = "123456789"
val intent = Intent(Intent.ACTION_CALL)
intent.data = Uri.parse("tel:$phoneNumber")
startActivity(intent)

🔒 2. Gestión de permisos

Dado que realizar llamadas telefónicas requiere permisos sensibles, la app solicita autorización al usuario antes de ejecutar la acción.
Esto se gestiona mediante el método requestPermissions() y la verificación en onRequestPermissionsResult().

Fragmento típico:

if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
    != PackageManager.PERMISSION_GRANTED) {
    ActivityCompat.requestPermissions(this,
        arrayOf(Manifest.permission.CALL_PHONE), 1)
}

📱 3. AndroidManifest.xml

Define los permisos necesarios y la Activity principal:

<uses-permission android:name="android.permission.CALL_PHONE"/>

<application
    android:allowBackup="true"
    android:label="CallPhoneSOS"
    android:supportsRtl="true">
    
    <activity android:name=".MainActivity">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
    </activity>

</application>

🎨 4. Interfaz de usuario (res/layout)

En el archivo activity_main.xml, se define un diseño simple con un botón grande y visible, para facilitar el acceso rápido:

<Button
    android:id="@+id/btnEmergencyCall"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="LLAMAR EMERGENCIA"
    android:backgroundTint="@color/red"
    android:textColor="@android:color/white"
    android:padding="16dp" />


El botón está vinculado en Kotlin mediante:

findViewById<Button>(R.id.btnEmergencyCall).setOnClickListener {
    makeEmergencyCall()
}

🧠 Lógica principal

El usuario abre la app.

Se muestra un botón principal de emergencia.

Al pulsarlo:

Se verifica el permiso de llamada.

Si el permiso está concedido, se ejecuta el Intent.ACTION_CALL.

Si no, se solicita permiso al usuario.

Se realiza la llamada al número predefinido.

🧪 Posibles mejoras

Enviar mensaje de texto automático (SMS) con ubicación.

Guardar un historial de llamadas o eventos.

Añadir configuración personalizada para múltiples contactos.

Implementar notificaciones push para alertar a familiares o servicios.

Diseño adaptado a accesibilidad (fuentes grandes, contraste, vibración).

🧰 Tecnologías utilizadas

Lenguaje: Kotlin

Entorno: Android Studio

Versión de Gradle: Kotlin DSL (.kts)

SDK Android: 33+

Permisos: CALL_PHONE