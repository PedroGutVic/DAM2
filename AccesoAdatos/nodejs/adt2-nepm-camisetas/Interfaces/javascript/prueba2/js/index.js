function nombre__completo() {
 return nombre + " " + apellidos;
 }

 var nombre = 'Luis';
 var apellidos = "Molina";

 console.log(nombre__completo());

 let personas = new Array(nombre__completo(), "Antonio Perez");
 console.log(personas[1]);
 console.log(personas[2]);