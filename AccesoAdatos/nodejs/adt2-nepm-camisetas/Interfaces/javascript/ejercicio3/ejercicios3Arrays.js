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

//i. Elimina dos elementos con delete.

//j. Calcula el producto de todos los números del array “datos” con forEach.

//k. Cada elemento x del array “datos” debe cambiarse por x*x. Usa forEach.

//l. Crea un nuevo array con map recorriendo cada elemento x de “datos”, donde cada elemento sea un string “El valor es: x”. Usa template strings.

//m. Crea un nuevo array mediante map que incremente cada elemento de “datos” en 5 unidades.

//n. Mediante filter, quédate con los números impares en un nuevo array impares.

//o. Usa find para buscar el número 13.

//p. Usa every para comprobar si todos los números son positivos.

//q. Calcula la sumatoria del array “datos” mediante reduce.

//r. Calcula el valor más pequeño del array mediante reduce.

//s. Usa flat para aplanar el array multidimensional que creaste anteriormente.

//t. Tenemos la cadena: “Vamos a usar flatMap. Es igual que map. Pero aplana los arrays”. Separa mediante split las distintas frases. Después, mediante map, quita los espacios sobrantes (trim). A continuación, usa flatMap para extraer todas las palabras de cada frase en un único array.

//u. Crea el array a = [1,2,3,4,5] y b = [6,7,8,9,10] con literales. Concatena los arrays a y b con concat. Después, usa el operador spread. Crea una variable const cola. Usa unshift y shift para añadir y quitar elementos. Dado el array resultante de la concatenación de a y b, obtén el subarray desde el índice 2 hasta el penúltimo elemento (slice). Usa splice para quitar los 2 últimos elementos de un array.

//v. Rellena con fill un array de 100 elementos con ‑1.

//w. Crea un array de cadenas. Busca con indexOf una cadena.

//x. Comprueba si la cadena “hola” está dentro del array anterior.

//y. Ordena la lista de cadenas anterior de forma alfabética con sort.

//z. Crea un array vacío de 50 posiciones. Con forEach, asigna valores aleatorios entre 0 y 100. Después, ordena con sort de menor a mayor. Cambia y ordena de mayor a menor.

//aa. Usa reverse para invertir el array anterior