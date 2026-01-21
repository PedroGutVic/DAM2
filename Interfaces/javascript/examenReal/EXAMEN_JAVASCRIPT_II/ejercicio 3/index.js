mural= document.getElementById("mural");
NewPostAutor = document.getElementById("DondeAutor");

fetch('http://localhost:3000/posts')
.then(response => response.json())
.then(data =>{
    data.forEach(product => {
        fetch('http://localhost:3000/users/'+product.id_author)
        .then(response => response.json())
        .then(dataUser => {
            mural.innerHTML += `<p>El autor es : ${dataUser.name} <br><br> post: ${product.text}  <br><br>likes : ${product.likes} <button onclick="MeGusta(${product.id})">Me gusta</button></p>`
            mural.innerHTML += "---------------------------------------------------------"
        })


    })
})

fetch('http://localhost:3000/users/')
    .then(response => response.json())
    .then(data => {
        data.forEach(autor => {
            NewPostAutor.innerHTML += `<option id="${autor.id}">${autor.name}</option>`
        })
    })

function MeGusta(id) {
    fetch('http://localhost:3000/posts/' + id)
        .then(response => response.json())
        .then(data =>{
            let numNewLikes = data.likes + 1
            console.log(data.likes)
            fetch("http://localhost:3000/posts/" + data.id, {
                                            method: "PATCH",
                                            headers: {
                                                "Content-Type": "application/json",
                                            },
                                            body: JSON.stringify({
                                                likes: numNewLikes
                                            })
                                        });

        })
}
