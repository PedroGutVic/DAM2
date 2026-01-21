package com.pedrogv.lambdaclass DialogoLambda {
    private lateinit var onClienteadd :  (String , String)-> Unit

    fun setOnClienteListener (add: (String , String) -> Unit){
        onClienteadd = add
    }
    fun mostrar (){
        val id ="4"
        val nombre = "Pepito"
        println("simulando la captura de datos")
        onClienteadd
    }
}