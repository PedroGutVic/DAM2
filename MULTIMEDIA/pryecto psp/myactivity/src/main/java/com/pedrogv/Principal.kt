class MyActivity: OperacionesCliente{
    private var clientes : MutableList<Cliente>

    init{
        clientes = Repo.listClientes.toMutableList()
    }

    fun onButtonClick(){

    }

    override fun add(id: String, nombre: String): Cliente {
        val nuevo = Cliente (id , nombre)
        clientes.add(nuevo)
        println("Clientes añadidos con id = $id y nombre = $nombre")
        return nuevo

    }
}

fun onButtonClick() {
    val dialogo = Dialogo()
    dialogo.setListener(this)
    dialogo.mostrar()
}

fun main() {
    val p = MyActivity()
}

fun init(){
    println("Simulando click en el boton para abrir el dialogo y captura")
    Thread.sleep(2000)
    onButtonClick()
    println("Muestro todos los registros")
    Cliente.foreach(
        println(it)
    )
    println("Fin")
}