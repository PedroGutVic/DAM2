// CamisetaFormFragment.kt
package com.pedrogv.camisetas.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.pedrogv.camisetas.MainActivity
import com.pedrogv.camisetas.R
import com.pedrogv.camisetas.models.Camiseta

class CamisetaFormFragment : Fragment() {

    // Argumento para la Edición
    private var camisetaId: Int = -1
    private var camisetaToEdit: Camiseta? = null

    // Vistas
    private lateinit var etNombre: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etImagenUrl: EditText
    private lateinit var btnSave: Button
    private lateinit var tvTitle: TextView

    companion object {
        private const val ARG_CAMISETA_ID = "camiseta_id"

        fun newInstance(camisetaId: Int = -1): CamisetaFormFragment {
            return CamisetaFormFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CAMISETA_ID, camisetaId)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Recuperar el ID de los argumentos (si existe)
        arguments?.let {
            camisetaId = it.getInt(ARG_CAMISETA_ID)
        }

        // 2. Si el ID es válido, buscar la camiseta a editar
        if (camisetaId != -1) {
            camisetaToEdit = (activity as MainActivity).controller.findCamisetaById(camisetaId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Debes crear un layout llamado fragment_camiseta_form.xml
        return inflater.inflate(R.layout.fragment_camiseta_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar vistas del formulario
        etNombre = view.findViewById(R.id.etNombre)
        etPrecio = view.findViewById(R.id.etPrecio)
        etImagenUrl = view.findViewById(R.id.etImagenUrl)
        btnSave = view.findViewById(R.id.btnSaveCamiseta)
        tvTitle = view.findViewById(R.id.tvFormTitle)

        // 3. Si es Edición, precargar datos (Placeholder)
        camisetaToEdit?.let { camiseta ->
            tvTitle.text = "Editar Camiseta"
            etNombre.setText(camiseta.nombre)
            etPrecio.setText(camiseta.precio.toString())
            etImagenUrl.setText(camiseta.imagenUrl.toString()) // Asumo que imagenUrl es un String para Glide, aunque en el modelo es Int
            btnSave.text = "Actualizar"
        } ?: run {
            tvTitle.text = "Añadir Camiseta"
            btnSave.text = "Guardar"
        }

        // 4. Lógica de Guardado/Actualización
        btnSave.setOnClickListener {
            saveOrUpdateCamiseta()
        }
    }

    private fun saveOrUpdateCamiseta() {
        // Validación básica
        val nombre = etNombre.text.toString()
        val precioStr = etPrecio.text.toString()
        val imagenUrlStr = etImagenUrl.text.toString()

        if (nombre.isBlank() || precioStr.isBlank() || imagenUrlStr.isBlank()) {
            // Mostrar error al usuario
            return
        }

        val precio = precioStr.toDoubleOrNull() ?: return
        val imagenUrl = imagenUrlStr.toIntOrNull() ?: 0 // Usar el tipo de dato de tu modelo (Int)

        if (camisetaToEdit == null) {
            // AÑADIR (ID será asignado en el DAO)
            val newCamiseta = Camiseta(id = 0, nombre = nombre, precio = precio, imagenUrl = imagenUrl)
            (activity as MainActivity).controller.addCamiseta(newCamiseta)
        } else {
            // EDITAR (Usar el ID existente)
            val updatedCamiseta = Camiseta(id = camisetaId, nombre = nombre, precio = precio, imagenUrl = imagenUrl)
            (activity as MainActivity).controller.updateCamiseta(updatedCamiseta)
        }

        // 5. Volver a la lista después de guardar/actualizar
        parentFragmentManager.popBackStack()
    }
}