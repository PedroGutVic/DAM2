//Ejercicios IV sobre lógica de programación
//1. Crea una función que cuente el número de vocales de una cadena de caracteres.
function contar(cadena) {
    const vocales = "aeiouAEIOU";
    let contador = 0;
    for (i = 0; i < cadena.length; i++) {
        if (vocales.includes(cadena[i])) {
            contador++;
        }
    }
    return contador;
}
//2. Crea una función que determine si una cadena es un palíndromo, es decir, que se lee igual hacia
//delante que hacia atrás.

function palindromo(cadena) {
    aux = "";
    for (i = 0; i < cadena.length; i++) {
        aux += cadena[cadena.length - 1 - i];
    }
    console.log(aux);
    if (cadena === aux) {
        return true;
    } else {
        return false;
    }
}

//3. Crea una función que capitalice una cadena de texto. Es decir que todas las palabras empiecen
//por mayúscula.

function FirstCap(cadena) {
    aux = cadena[0].toUpperCase();
    for (i = 1; i < cadena.length; i++) {
        if (cadena[i] !== " ") {
            aux += cadena[i].toLowerCase();
        } else {
            aux += " " + cadena[i + 1].toUpperCase();
            i++;
        }
    }
    console.log(aux);
}

//4. Dado un array de cadenas y una longitud n, crea una función que filtre el array dejando solo las
//cadenas de menor longitud que n.

function arrayCadena(array, n) {
    return array.filter((cadena) => cadena.length < n);
}

//5. Crea una función que cree el acrónimo de una cadena de caracteres tomando la primera letra de
//cada palabra y convirtiéndola a mayúscula. Por ejemplo la frase anterior sería CUFQCEADU….
function crearAcronimo(frase) {
    return frase
        .split(" ")
        .map((palabra) => palabra.charAt(0).toUpperCase())
        .join("");
}

console.log(
    crearAcronimo(
        "Crea una función que cree el acrónimo de una cadena de caracteres"
    )
); // CUFQCEADU

//6. Crea una función que cuente las frases, palabras y letras presentes en un texto.
function contarFrasesPalabrasLetras(texto) {
    const frases = texto.split(".").filter((frase) => frase.trim() !== "");
    const palabras = texto
        .split(/\s+/)
        .filter((palabra) => palabra.trim() !== "");
    const letras = texto.replace(/[^a-zA-Z]/g, "").length;

    return {
        frases: frases.length,
        palabras: palabras.length,
        letras: letras,
    };
}

const texto = "Esta es una frase. Y esta es otra. ¡Hola Mundo!";
console.log(contarFrasesPalabrasLetras(texto)); // {frases: 3, palabras: 6, letras: 23}

//7. Crea una función que identifique si hay elementos duplicados en un array.
function tieneDuplicados(array) {
    return new Set(array).size !== array.length;
}

console.log(tieneDuplicados([1, 2, 3, 4])); // false
console.log(tieneDuplicados([1, 2, 2, 4])); // true

//8. Crea una función que debe retornar verdadero si alguno de los elementos de un array está re‑
//petido n veces.
function elementoRepetidoNVeces(array, n) {
    const count = {};
    for (let elem of array) {
        count[elem] = (count[elem] || 0) + 1;
    }

    return Object.values(count).some((val) => val === n);
}

console.log(elementoRepetidoNVeces([1, 2, 2, 4, 4, 4], 3)); // true
console.log(elementoRepetidoNVeces([1, 2, 2, 4], 3)); // false

//9. Crea un array que intercale dos arrays dados. Por ejemplo dados [a,b,c,d] y [1,2,3,4] el resultado
//sería [a,1,b,2,c,3,d,4]
function intercalarArrays(arr1, arr2) {
    const resultado = [];
    for (let i = 0; i < arr1.length; i++) {
        resultado.push(arr1[i], arr2[i]);
    }
    return resultado;
}

console.log(intercalarArrays(["a", "b", "c", "d"], [1, 2, 3, 4])); // ['a', 1, 'b', 2, 'c', 3, 'd', 4]

//10. Crea una función que rote los elementos de un array n posiciones. Por ejemplo, dado el array
//[1,2,3,4,5,6] y el número 2 el resultado será: [5,6,1,2,3,4]

function rotarArray(arr, n) {
    const len = arr.length;
    n = n % len; // Asegurarse de que no sea más grande que el tamaño del array
    return arr.slice(-n).concat(arr.slice(0, len - n));
}

console.log(rotarArray([1, 2, 3, 4, 5, 6], 2)); // [5, 6, 1, 2, 3, 4]

//11. Crea una función que elimine de una cadena los caracteres dados en un array.
function eliminarCaracteres(cadena, caracteres) {
    let resultado = cadena;
    caracteres.forEach((caracter) => {
        resultado = resultado.split(caracter).join("");
    });
    return resultado;
}

console.log(eliminarCaracteres("Hola Mundo", ["o", " "])); // "HlaMund"

//12. Crea una función que rote una matriz de tamaño nxn, 90 grados a la derecha. Ejemplo: [1,2,3]
//[7, 4, 1] [4,5,6] => [8, 5, 2] [7,8,9] [9, 6, 3]
function rotarMatriz(matriz) {
    const n = matriz.length;
    let nuevaMatriz = [];

    for (let i = 0; i < n; i++) {
        nuevaMatriz[i] = [];
        for (let j = 0; j < n; j++) {
            nuevaMatriz[i][j] = matriz[n - j - 1][i];
        }
    }

    return nuevaMatriz;
}

const matriz = [
    [1, 2, 3],
    [7, 4, 1],
    [4, 5, 6],
];

console.log(rotarMatriz(matriz)); // [[4, 7, 9], [5, 8, 6], [2, 1, 3]]

//13. Crea una función que determine si los paréntesis presentes en una cadena de texto están balan‑
//ceados. Por ejemplo (a(b)) → Balanceado, (a(b(a)) → No balanceado.
function balancearParentesis(cadena) {
    const stack = [];
    for (let i = 0; i < cadena.length; i++) {
        const char = cadena[i];
        if (char === "(") {
            stack.push(char);
        } else if (char === ")") {
            if (stack.length === 0) return false;
            stack.pop();
        }
    }
    return stack.length === 0;
}

console.log(balancearParentesis("(a(b))")); // true
console.log(balancearParentesis("(a(b(a))")); // false

//14. Busca una submatriz dentro de una matriz más grande. El resultado debe ser las coordenadas
//donde se encuentra dicha matriz.
function buscarSubmatriz(matriz, submatriz) {
    const filas = matriz.length;
    const columnas = matriz[0].length;
    const subFilas = submatriz.length;
    const subColumnas = submatriz[0].length;

    for (let i = 0; i <= filas - subFilas; i++) {
        for (let j = 0; j <= columnas - subColumnas; j++) {
            let encontrado = true;
            for (let k = 0; k < subFilas; k++) {
                for (let l = 0; l < subColumnas; l++) {
                    if (matriz[i + k][j + l] !== submatriz[k][l]) {
                        encontrado = false;
                        break;
                    }
                }
                if (!encontrado) break;
            }
            if (encontrado) return [i, j];
        }
    }
    return null; // Si no se encuentra la submatriz
}

const matrizGrande = [
    [1, 2, 3, 4],
    [5, 6, 7, 8],
    [9, 10, 11, 12],
];

const submatriz = [
    [6, 7],
    [10, 11],
];

console.log(buscarSubmatriz(matrizGrande, submatriz)); // [1, 1]

//15. Crea una función que verifique si una matriz de 9x9 es una solución de un sudoku. Una cuadrí‑
//cula válida de Sudoku es aquella que cumple las siguientes condiciones:
//a. Filas Únicas: Cada fila debe contener los números del 1 al 9 sin repetición.
//b. Columnas Únicas: Cada columna debe contener los números del 1 al 9 sin repetición.
//c. Subcuadrículas Únicas: Cada una de las nueve subcuadrículas de 3x3 debe contener los
//números del 1 al 9 sin repetición.
function esSolucionSudoku(matriz) {
    const verificarFila = (fila) => new Set(fila).size === 9;
    const verificarColumna = (columnaIdx) => {
        const columna = matriz.map((fila) => fila[columnaIdx]);
        return new Set(columna).size === 9;
    };
    const verificarSubcuadricula = (filaIdx, columnaIdx) => {
        const subcuadricula = [];
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                subcuadricula.push(matriz[filaIdx + i][columnaIdx + j]);
            }
        }
        return new Set(subcuadricula).size === 9;
    };

    // Verificar filas y columnas
    for (let i = 0; i < 9; i++) {
        if (!verificarFila(matriz[i]) || !verificarColumna(i)) {
            return false;
        }
    }

    // Verificar subcuadrículas
    for (let i = 0; i < 9; i += 3) {
        for (let j = 0; j < 9; j += 3) {
            if (!verificarSubcuadricula(i, j)) {
                return false;
            }
        }
    }

    return true;
}

const sudokuValido = [
    [5, 3, 4, 6, 7, 8, 9, 1, 2],
    [6, 7, 2, 1, 9, 5, 3, 4, 8],
    [1, 9, 8, 3, 4, 2, 5, 6, 7],
    [8, 5, 9, 7, 6, 1, 4, 2, 3],
    [4, 2, 6, 8, 5, 3, 7, 9, 1],
    [7, 1, 3, 9, 2, 4, 8, 5, 6],
    [9, 6, 1, 5, 3, 7, 2, 8, 4],
    [2, 8, 7, 4, 1, 9, 6, 3, 5],
    [3, 4, 5, 2, 8, 6, 1, 7, 9],
];

console.log(esSolucionSudoku(sudokuValido)); // true

//Ejercicios V de Javascript
//1. Crea un programa que genere un array con 1000 números aleatorios del 0 al 99.

array = []
function rellenarArray(array){
    for ( i = 0; i < 100; i++) {
        array[i] = Math.floor(Math.random()*1000)
    }
}

//a. Crea una función que calcule la media aritmética.

function media(array){

    sum=0
    contador=0
    array.forEach(element => {
        sum += element
        contador++
    });
    mediaSum = sum / contador
    return mediaSum
}

//b. Calcula la frecuencia de cada número del 0 al 99. Es decir, si el número 0 aparece 80 veces
//en el array de 1000 posiciones, se guardará un 80 en la posición 0 del nuevo array. Si el
//número 1 aparece 50 veces, se guardará un 50 en la posición 1, etc.

function frecuencia(array){
    array2 = []
    for ( i = 0; i < 100; i++) {
            array2[i] = i
    }
    contadorAUX = 0

    for (let i = 0; i < array2.length; i++) {
        for (let j = 0; j < array.length; j++) {
            if (array[j] == array2[i]) {
                contadorAUX++
            }
        }
        array2[i] += " " + String(contadorAUX)
        console.log(array2[i])
        contadorAUX = 0
    }
}


//c. Crea una función que ordene el array de menor a mayor sin usar métodos de Javascript.

function ordenar(array){

    aux =0
    for (let i = 0; i < array.length; i++) {
        for (let j = 0; j < array.length; j++) {
            if(array[i]<array[j]){
                aux = array[i]
                array[i] = array[j]
                array[j] = aux
            }
        }
    }
}

//d. Ahora, usa una función de Javascript para realizar la ordenación.

function ordenar2(array2){
    array2.sort()
}

//2. Crea una función que calcule el factorial de un número usando un bucle.

function factorial(numero){
    aux =numero
    for (let i = 1; i < numero; i++) {
        aux=aux*i
        console.log(aux)
    }
}

//3. Crea una función que busque todas las ocurrencias de una palabra en un texto dado. La función
//retorna un array con las posiciones encontradas.

function buscarOcurrencias(texto, palabra) {
    const posiciones = [];
    let pos = texto.indexOf(palabra);
    while (pos !== -1) {
    posiciones.push(pos);
    pos = texto.indexOf(palabra, pos + 1);
    }
    return posiciones;
}

// Ejemplo:
console.log(buscarOcurrencias("hola mundo hola hola", "hola"));
// [0, 11, 16]


//4. El programa FizzBuzz se ha usado habitualmente para descartar candidatos en pruebas de se‑
//lección de personal. Consiste en escribir un programa que muestre en pantalla los números del
//1 al 100, sustituyendo los múltiplos de 3 por la palabra “fizz”, los múltiplos de 5 por “buzz”, y los
//múltiplos de ambos, es decir, los múltiplos de 3 y 5 (o de 15), por la palabra “fizzbuzz”.

for (let i = 1; i <= 100; i++) {
    if (i % 15 === 0) console.log("fizzbuzz");
    else if (i % 3 === 0) console.log("fizz");
    else if (i % 5 === 0) console.log("buzz");
    else console.log(i);
}


//5. Crea una página que genere un acertijo matemático simple formado por sumas y restas. El usua‑
//rio inserta la respuesta y el script le indica si ha acertado o no.

//6. Crea una página web que genere contraseñas aleatorias con una mezcla de letras, números
//y símbolos. La página tiene un campo de entrada numérico en el que el usuario especifica el
//tamaño de la contraseña a generar. La contraseña se mostrará al pulsar un botón.

//7. Implementa un script que cuente el número de vocales y consonantes en una cadena.
function contarVocalesConsonantes(cadena) {
    cadena = cadena.toLowerCase().replace(/[^a-záéíóúü]/g, "");
    const vocales = cadena.match(/[aeiouáéíóúü]/g) || [];
    const consonantes = cadena.match(/[^aeiouáéíóúü]/g) || [];
    return { vocales: vocales.length, consonantes: consonantes.length };
}

console.log(contarVocalesConsonantes("Hola Mundo!"));
// { vocales: 4, consonantes: 5 }

//8. Crea una página web con un formulario que contenga los campos nombre, correo electrónico
//y edad. Cuando el usuario pulse el botón enviar, se validará el formulario comprobando que
//el nombre tiene más de 3 caracteres, la edad es un número entre 0 y 120, y el email se valida
//mediante una expresión regular.

//9. Implementa el cifrado César, un tipo de cifrado por sustitución en el que cada letra en el texto
//original es reemplazada por otra que se encuentra un número fijo de posiciones más adelante
//en el alfabeto. Crea una página web con un área de texto que contenga el texto original. Al pulsar
//un botón, se mostrará debajo, en otro área de texto, el texto cifrado.

//10. Crea una página web con un cronómetro que muestre minutos y segundos. Deberá contar con
//los botones Comenzar, Parar y Reiniciar. Ayúdate de las funciones setInterval, clearInterval, y
//setTimeout.

//11. Crea una página web que imprima, mediante un script, la tabla de multiplicar especificada por
//el usuario al cambiar un valor en un desplegable.

//12. Crea una página web para jugar a Piedra, Papel o Tijera contra la máquina. Gana el primero que
//gane 2 de 3 jugadas. En cualquier momento, el usuario puede interrumpir la partida e iniciar
//una nueva.
