// interfaceDao.kt
package com.pedrogv.camisetas.interfaces

import com.pedrogv.camisetas.models.Camiseta

interface InterfaceDao {
    fun getDataCamiseta(): List<Camiseta>
    fun insertCamiseta(camiseta: Camiseta) // Nuevo
    fun updateCamiseta(camiseta: Camiseta) // Nuevo
    fun findCamisetaById(id: Int): Camiseta? // Nuevo
}
