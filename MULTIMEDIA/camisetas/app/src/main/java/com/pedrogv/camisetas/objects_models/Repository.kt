// Repository.kt
package com.pedrogv.camisetas.objects_models

import com.pedrogv.camisetas.R
import com.pedrogv.camisetas.interfaces.InterfaceDao
import com.pedrogv.camisetas.models.Camiseta

object Repository : InterfaceDao {
    // CAMBIO CRÍTICO: Debe ser MutableList para poder añadir y actualizar
    val listTshirt : MutableList<Camiseta> = mutableListOf(
        Camiseta(1, "Camiseta Básica Blanca", 15.99, R.drawable.camiseta),
        Camiseta(2, "Camiseta Negra Clásica", 17.49, R.drawable.camiseta),
        Camiseta(3, "Camiseta Azul Marino", 18.99, R.drawable.camiseta),
        Camiseta(4, "Camiseta Roja Deportiva", 20.0, R.drawable.camiseta),
        Camiseta(5, "Camiseta Verde Militar", 19.5, R.drawable.camiseta),
        Camiseta(6, "Camiseta Amarilla Casual", 16.75, R.drawable.camiseta),
        Camiseta(7, "Camiseta Gris Melange", 18.25, R.drawable.camiseta),
        Camiseta(8, "Camiseta Estampada Retro", 21.0, R.drawable.camiseta),
        Camiseta(9, "Camiseta de Manga Larga Negra", 22.5, R.drawable.camiseta),
        Camiseta(10, "Camiseta Deportiva Blanca", 20.99, R.drawable.camiseta)
    )

    // Aquí devolvemos la lista mutable.
    override fun getDataCamiseta(): MutableList<Camiseta> {
        // Asegúrate de que este método devuelva MutableList si lo usas en Controller para initData
        // Aunque el DAO lo devuelve como List, el uso en Controller lo convierte.
        // Lo mantendremos como estaba en la interfaz (List<Camiseta>) pero el DAO accederá a la MutableList
        return listTshirt
    }

    override fun insertCamiseta(camiseta: Camiseta) {
        TODO("Not yet implemented")
    }

    override fun updateCamiseta(camiseta: Camiseta) {
        TODO("Not yet implemented")
    }

    override fun findCamisetaById(id: Int): Camiseta? {
        TODO("Not yet implemented")
    }
}