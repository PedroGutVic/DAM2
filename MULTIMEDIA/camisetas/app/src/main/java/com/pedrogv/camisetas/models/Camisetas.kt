package com.pedrogv.camisetas.models

data class Camiseta(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagenUrl: Int //String
){
    override fun toString(): String {
        return "Camiseta(id=$id, nombre='$nombre', precio=$precio, imagenUrl='$imagenUrl')"
    }
}
