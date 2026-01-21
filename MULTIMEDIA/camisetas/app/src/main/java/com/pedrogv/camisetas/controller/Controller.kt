package com.pedrogv.camisetas.controller

import android.content.Context
import android.widget.Toast
import com.pedrogv.camisetas.MainActivity // Importar MainActivity
import com.pedrogv.camisetas.adapter.AdapterCamisetas
import com.pedrogv.camisetas.dao.DaoCamisetas
import com.pedrogv.camisetas.databinding.ActivityMainBinding
import com.pedrogv.camisetas.models.Camiseta

class Controller(
    private val context: Context,
    private val binding: ActivityMainBinding
) {
    private lateinit var listTshirt: MutableList<Camiseta>
    // 1. CORRECCIÓN: Declarar la instancia del adapter aquí
    private lateinit var adapter: AdapterCamisetas

    init {
        initData()
    }

    /** Inicializa la lista desde el DAO */
    private fun initData() {
        listTshirt = DaoCamisetas.myDao.getDataCamiseta().toMutableList()
    }

    /** Muestra un Toast y logea los datos */
    fun loggOut() {
        Toast.makeText(context, "He mostrado los datos en pantalla", Toast.LENGTH_LONG).show()
        listTshirt.forEach { println(it) }
    }

    /** Configura el RecyclerView con el Adapter */
    fun setAdapter() {
        val activity = context as MainActivity
        // 2. CORRECCIÓN: Inicializar la variable 'adapter' de la clase
        adapter = AdapterCamisetas(listTshirt) { camisetaId ->
            activity.showFormFragment(camisetaId)
        }
        binding.myRecyclerView.adapter = adapter
    }

    fun addCamiseta(camiseta: Camiseta) {
        DaoCamisetas.myDao.insertCamiseta(camiseta)
        initData()
        // 3. CORRECCIÓN: Usar la instancia 'adapter'
        adapter.listTshit = listTshirt
        adapter.notifyDataSetChanged()
        Toast.makeText(context, "Camiseta añadida!", Toast.LENGTH_SHORT).show()
    }

    fun updateCamiseta(camiseta: Camiseta) {
        DaoCamisetas.myDao.updateCamiseta(camiseta)
        initData()
        // 4. CORRECCIÓN: Usar la instancia 'adapter'
        adapter.listTshit = listTshirt
        adapter.notifyDataSetChanged()
        Toast.makeText(context, "Camiseta actualizada!", Toast.LENGTH_SHORT).show()
    }

    fun findCamisetaById(id: Int): Camiseta? {
        return DaoCamisetas.myDao.findCamisetaById(id)
    }
}