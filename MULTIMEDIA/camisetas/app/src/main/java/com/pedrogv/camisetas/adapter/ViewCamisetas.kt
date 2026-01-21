package com.pedrogv.camisetas.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pedrogv.camisetas.models.Camiseta
import android.widget.TextView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pedrogv.camisetas.R

class ViewCamisetas(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvNombre: TextView = itemView.findViewById(R.id.txtNombre)
    private val tvPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
    private val ivCamiseta: ImageView = itemView.findViewById(R.id.imgCamiseta)

    // Método para “mapear” los datos del modelo al layout
    fun renderize(camiseta: Camiseta) {
        tvNombre.text = camiseta.nombre
        tvPrecio.text = "$${camiseta.precio}" // Formato de precio
        Glide
            .with(itemView.context)
            .load(camiseta.imagenUrl)
            .centerCrop()
            .into(ivCamiseta)
    }
}
