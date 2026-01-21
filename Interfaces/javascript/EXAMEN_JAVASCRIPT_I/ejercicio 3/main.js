import saludar from "./saludo";
import obtenerMomentoDelDia from "./saludo";
import mayus from "./utilidades";

const nombre = "Luis";

const saludo = saludar(nombre);
const momento = obtenerMomentoDelDia();
const saludoCompleto = `${momento}. ${saludo}!`;

const saludoMayus = mayus(saludoCompleto);

document.getElementById("resultado").innerHTML = `<p>${saludoMayus}</p>`;
