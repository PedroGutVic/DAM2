

const nombre = "Luis";

const saludo = saludar(nombre);
const momento = obtenerMomentoDelDia();
const saludoCompleto = `${momento}. ${saludo}!`;

const saludoMayus = convertirMayus(saludoCompleto);

document.getElementById("resultado").innerHTML = `<p>${saludoMayus}</p>`;
