package com.pedrogv.lambda

class MyActivity2 {
    private var clientes : MutableList<Cliente>

    init{
        clientes = Repo.listClientes.toMutableList()
    }
}

    fun init(){
        println("Simulando click en el boton para abrir el dialogo y captura")
        Thread.sleep(2000)
        onButtonClick()
        println("Muestro todos los registros")
        clientes.forEach(
            println(it)
        )
        println("Fin")
    }

    private fun onButtonClick() {
        val dialogo = DialogoLambda()
        dialogo.setOnClienteListener()
        dialogo.mostrar()
    }

    fun main() {
        val p = MyActivity2()
    }