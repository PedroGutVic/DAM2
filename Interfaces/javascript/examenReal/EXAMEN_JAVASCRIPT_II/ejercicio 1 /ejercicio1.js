//


// b)
function contarVocales(frase) {
    const vocales = 'aeiouAEIOU';
    let contador = 0;
    for (let i = 0; i < frase.length; i++) {
        if (vocales.includes(frase[i])) contador++;
    }
    return contador;
}

// c)
function esPalindromo(cadena) {
    const limpia = cadena.toLowerCase().replace(/\s+/g, '');
    const invertida = limpia.split('').reverse().join('');
    return limpia === invertida;
}

// d)
const arr = [];
for (let i = 10; i <= 50; i += 5) {
    arr.push(i);
}
const [primero, ...resto] = arr;

// e)
const cuadrados = arr.map(n => n * n);
const menoresMil = cuadrados.filter(n => n < 1000);
const algunPar = menoresMil.some(n => n % 2 === 0);

// f)
const copia = [...arr, 60];

// g)
function mostrarPersona(persona) {
    for (let key in persona) {
        console.log(key + ':', persona[key]);
    }
}

const persona = {
    nombre: 'Juan',
    apellidos: 'Pérez López',
    edad: 30
};

mostrarPersona(persona);

// h)
const { nombre, apellidos } = persona;
