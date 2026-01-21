myDiv = document.getElementById("ListarVideojuegos");



fetch("http://localhost:3000/productos")
    .then((response) => response.json())
    .then((data) => {
        data.forEach((product) => {
            myDiv.innerHTML += `<img src="${product.imagen}"> <br> <p> nombre : ${product.nombre} $ ${product.pvp} <br>  <p>Descripcion : ${product.descripcion}</p> <br> <button onclick="comprar(${product.id})">Comprar</button></p>`;
        });
    });


comprobar = false;
function comprar(id) {
    fetch("http://localhost:3000/productos/" + id)
        .then((response) => response.json())
        .then((data) => {

            
            const nuevoProducto = {
                productoId: parseInt(data.id)

            };


            fetch("http://localhost:3000/carrito")
                .then((response) => response.json())
                .then(data => {
                    console.log(comprobar)
                    data.forEach((element) => {
                        console.log(id)
                        console.log(element.productoId)
                        if (id == element.productoId  ) {
                            comprobar = true
                            alert("Ya existe el producto")
                            
                        }else{
                        }
                        console.log("probar")
                    })
                    if(!comprobar){
                            console.log("Entrado en POST")
                            fetch("http://localhost:3000/carrito/", {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/json",
                                },
                                body: JSON.stringify(nuevoProducto),
                            });
                            comprobar = false
                        console.log(comprobar)
                        }
                })
        });
}




function irCarrito() {
    window.location.href = "./carrito.html";
}
