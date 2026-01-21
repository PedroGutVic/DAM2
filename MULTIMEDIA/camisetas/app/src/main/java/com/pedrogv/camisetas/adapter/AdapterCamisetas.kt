package com.pedrogv.camisetas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button

import androidx.recyclerview.widget.RecyclerView
import com.pedrogv.camisetas.R
import com.pedrogv.camisetas.models.Camiseta

typealias OnEditClickListener = (camisetaId: Int) -> Unit

class AdapterCamisetas(
    var listTshit : MutableList<Camiseta>,
    private val onEditClick: OnEditClickListener // 2. NUEVO: Recibir el listener en el constructor
) : RecyclerView.Adapter<ViewCamisetas>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCamisetas {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutItemCamiseta = R.layout.card_view
        return ViewCamisetas(layoutInflater.inflate(layoutItemCamiseta, parent, false))
    }

    override fun onBindViewHolder(holder: ViewCamisetas, position: Int) {
        val camiseta = listTshit[position] // 3. Definir la variable camiseta
        holder.renderize(camiseta)

        holder.itemView.findViewById<Button>(R.id.delete).setOnClickListener {
            eliminarItem(position)
        }

        // 4. Implementar el Listener de Editar
        holder.itemView.findViewById<Button>(R.id.edit).setOnClickListener {
            onEditClick(camiseta.id) // Ahora sí se llama al callback con el ID
        }
    }

    override fun getItemCount(): Int = listTshit.size

    fun eliminarItem(posicion: Int) {
        listTshit.removeAt(posicion)
        notifyItemRemoved(posicion)
        notifyItemRangeChanged(posicion, listTshit.size)
    }
}