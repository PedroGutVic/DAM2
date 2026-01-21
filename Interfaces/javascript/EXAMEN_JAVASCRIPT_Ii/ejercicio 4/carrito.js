carrito = document.getElementById('carrito');


cargarCarrito();

function cargarCarrito() {
    fetch('http://localhost:3000/carrito')
        .then(response => response.json())
        .then(data => {
            data.forEach(element => {
                fetch('http://localhost:3000/products/' + element.productoId)
                    .then(product => {
                        carrito.innerHTML += `<img src="${product.imagen}"> <br> <p> nombre : ${product.nombre} $ ${product.pvp} <br>  <p>Descripcion : ${product.descripcion}</p> <br> <button onclick="borrar(${product.id})">Borrar</button></p>`
                    })

            });

        })
        .catch(error => {
            carrito.innerHTML += "<h3>El carrito está vacío</h3>"
        })
}

function borrar(id) {
    fetch("http://localhost:3000/carrito/" + id, {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
        }
    })
        .then(response => {
            window.location.href = "./carrito.html";
        })
}
function volver() {
    window.location.href = "./index.html";
}