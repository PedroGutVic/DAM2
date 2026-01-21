class Dialogo {
    private lateinit var listener: OperacionesCliente

    fun setListener (miListener : OperacionesCliente){
        listener = miListener
    }
    fun mostrar (){
        val id ="4"
        val nombre = "Pepito"
        println("simulando la captura de datos")
        listener.add(id , nombre)
    }
}