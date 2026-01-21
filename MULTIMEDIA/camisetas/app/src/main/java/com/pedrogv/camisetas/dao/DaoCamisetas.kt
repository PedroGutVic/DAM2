package com.pedrogv.camisetas.dao


import com.pedrogv.camisetas.interfaces.InterfaceDao
import com.pedrogv.camisetas.models.Camiseta
import com.pedrogv.camisetas.objects_models.Repository

class DaoCamisetas : InterfaceDao {
    companion object {
        val myDao: DaoCamisetas by lazy{ //lazy delega a un primer acceso
            DaoCamisetas() //Me creo sólo este objeto una vez.
        }
    }
    //Método que accede a la BBDD y devuelve todos los datos
    override fun getDataCamiseta(): List<Camiseta> = Repository.listTshirt

    override fun insertCamiseta(camiseta: Camiseta) {
        // Asegura un ID único (simulación de autoincremento)
        val newId = (Repository.listTshirt.maxOfOrNull { it.id } ?: 0) + 1
        Repository.listTshirt.add(camiseta.copy(id = newId)) // Usa copy para asegurar el nuevo ID
    }

    override fun updateCamiseta(camiseta: Camiseta) {
        val index = Repository.listTshirt.indexOfFirst { it.id == camiseta.id }
        if (index != -1) {
            Repository.listTshirt[index] = camiseta // Reemplaza el objeto
        }
    }

    override fun findCamisetaById(id: Int): Camiseta? {
        return Repository.listTshirt.find { it.id == id }
    }
}