//16 Ejercicios III sobre arrays

//a. Crea un array “datos” vacío con un literal.

const datos = [];

//b. Añade a “datos” los números del 1 al 50 con un bucle for.
for (let i = 1; i <= 50; i++) {
  datos.push(i);
}

//c. Elimina los elementos del 25 al 50 asignando un nuevo tamaño a la propiedad length.
datos.length = 24;

//d. Usa el operador spread para hacer una copia del array anterior.
datos_copia = [...datos];
//e. Crea un array de tamaño 50 con el constructor Array.
const datos2 = new Array(50);

//f. Copia el array anterior a otro con la factoría from.
const datos3 = Array.from(datos2);

//g. Crea un array multidimensional de 10 filas (i) y 10 columnas (j). Inicializa cada celda con el valor i*j.
const datos4 = Array.from({ length: 10 }, (_, i) =>
  Array.from({ length: 10 }, (_, j) => i * j)
);
console.log(datos4);

//h. Crea un array con la factoría of con los números del 1 al 5. Después, añade un elemento en la posición 10 y otro en la 50. Recorre el array con un for imprimiendo los valores, y después con forEach. ¿Cuál es la diferencia? ¿Cuál es el tamaño del array?
const datos5 = Array.of(1, 2, 3, 4, 5);
datos5[10] = 11;
datos5[50] = 51;

for (let i = 0; i < datos5.length; i++) {
  console.log(`for: ${datos5[i]}`);
}

datos5.forEach((value, index) => {
  console.log(`forEach: Index ${index}, Value ${value}`);
});

console.log(`Tamaño del array: ${datos5.length}`);
//i. Elimina dos elementos con delete.
delete datos5[10];
delete datos5[50];

//j. Calcula el producto de todos los números del array “datos” con forEach.
let producto = 1;
datos5.forEach((value) => {
  if (typeof value === "number") {
    producto *= value;
  }
});
console.log(`Producto de datos: ${producto}`);

//k. Cada elemento x del array “datos” debe cambiarse por x*x. Usa forEach.
datos5.forEach((value, index) => {
  if (typeof value === "number") {
    datos5[index] = value * value;
  }
});
console.log(`Array datos después de elevar al cuadrado: ${datos5}`);
//l. Crea un nuevo array con map recorriendo cada elemento x de “datos”, donde cada elemento sea un string “El valor es: x”. Usa template strings.
const datos6 = datos5.map((value) => `El valor es: ${value}`);
console.log(datos6);

//m. Crea un nuevo array mediante map que incremente cada elemento de “datos” en 5 unidades.
const datos7 = datos5.map((value) => value + 5);
console.log(datos7);

//n. Mediante filter, quédate con los números impares en un nuevo array impares.
const impares = datos5.filter((value) => value % 2 !== 0);
console.log(impares);

//o. Usa find para buscar el número 13.
const encontrado = datos5.find((value) => value === 13);
console.log(`Número 13 ${encontrado ? "encontrado" : "no encontrado"}`);

//p. Usa every para comprobar si todos los números son positivos.
const todosPositivos = datos5.every((value) => value > 0);
console.log(`Todos los números son positivos: ${todosPositivos}`);

//q. Calcula la sumatoria del array “datos” mediante reduce.
const sumatoria = datos5.reduce((acc, value) => acc + value, 0);
console.log(`Sumatoria de datos: ${sumatoria}`);

//r. Calcula el valor más pequeño del array mediante reduce.
const valorMinimo = datos5.reduce((min, value) => (value < min ? value : min), datos5[0]);
console.log(`Valor mínimo de datos: ${valorMinimo}`);

//s. Usa flat para aplanar el array multidimensional que creaste anteriormente.
const datos4Aplanado = datos4.flat();
console.log(`Array aplanado: ${datos4Aplanado}`);

//t. Tenemos la cadena: “Vamos a usar flatMap. Es igual que map. Pero aplana los arrays”. Separa mediante split las distintas frases. Después, mediante map, quita los espacios sobrantes (trim). A continuación, usa flatMap para extraer todas las palabras de cada frase en un único array.
const cadena = "Vamos a usar flatMap. Es igual que map. Pero aplana los arrays";
const palabras = cadena.split(".").map((frase) => frase.trim()).flatMap((frase) => frase.split(" "));
console.log(palabras);

//u. Crea el array a = [1,2,3,4,5] y b = [6,7,8,9,10] con literales. Concatena los arrays a y b con concat. Después, usa el operador spread. Crea una variable const cola. Usa unshift y shift para añadir y quitar elementos. Dado el array resultante de la concatenación de a y b, obtén el subarray desde el índice 2 hasta el penúltimo elemento (slice). Usa splice para quitar los 2 últimos elementos de un array.
const a = [1, 2, 3, 4, 5];
const b = [6, 7, 8, 9, 10];
const concatenado = a.concat(b);
const concatenadoSpread = [...a, ...b];

let cola = [...concatenado];
cola.unshift(0); // Añadir al inicio
console.log(`Después de unshift: ${cola}`);
const primerElemento = cola.shift(); // Quitar del inicio
console.log(`Después de shift (elemento quitado: ${primerElemento}): ${cola}`);

const subarray = concatenado.slice(2, concatenado.length - 1);
console.log(`Subarray desde índice 2 hasta penúltimo: ${subarray}`);

const arrayParaSplice = [...concatenado];
arrayParaSplice.splice(-2, 2); // Quitar los 2 últimos elementos
console.log(`Después de splice para quitar los 2 últimos elementos: ${arrayParaSplice}`);
//v. Rellena con fill un array de 100 elementos con ‑1.
const arrayFill = new Array(100).fill(-1);
console.log(arrayFill);
//w. Crea un array de cadenas. Busca con indexOf una cadena.
const arrayCadenas = ["hola", "mundo", "javascript"];
const indice = arrayCadenas.indexOf("mundo");
console.log(`Índice de "mundo": ${indice}`);

//x. Comprueba si la cadena “hola” está dentro del array anterior.
const contieneHola = arrayCadenas.includes("hola");
console.log(`¿Contiene "hola"? ${contieneHola}`);

//y. Ordena la lista de cadenas anterior de forma alfabética con sort.
const arrayCadenasOrdenado = [...arrayCadenas].sort();
console.log(`Array de cadenas ordenado: ${arrayCadenasOrdenado}`);

//z. Crea un array vacío de 50 posiciones. Con forEach, asigna valores aleatorios entre 0 y 100. Después, ordena con sort de menor a mayor. Cambia y ordena de mayor a menor.
const arrayAleatorio = new Array(50);
arrayAleatorio.forEach((_, i) => {
  arrayAleatorio[i] = Math.floor(Math.random() * 101);
});
arrayAleatorio.sort((a, b) => a - b);
console.log(`Array ordenado de menor a mayor: ${arrayAleatorio}`);
arrayAleatorio.sort((a, b) => b - a);
console.log(`Array ordenado de mayor a menor: ${arrayAleatorio}`);

//aa. Usa reverse para invertir el array anterior
arrayAleatorio.reverse();
console.log(`Array invertido: ${arrayAleatorio}`);