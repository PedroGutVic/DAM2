const misDecimos = [];
const EL_GORDO = 15072;
const EL_SEGUNDO = 25789;
const pedreas = [];


// a)
for (i = 0; i < 10; i++) {
    posibleNumero = Math.random() * 99999;
    for (j = 0; j < 10; j++) {
        if (pedreas[j] != posibleNumero) {
            pedreas[i] = posibleNumero;
            console.log("El numero no esta repetido ");
        } else {
            i--;
        }
    }
}


// b)
numeroAnterior_gordo;
numeroSiguiente_gordo;

numeroAnterior_Segundo;
numeroSiguiente_Segundo;

comprobante = false;

//comprobacion de que si es 0 o 99999 tenga bien su anterior o siguiente
if (EL_GORDO - 1 <= 0) {
    numeroAnterior_gordo = 99999;
    comprobante = true;
} else {
    if (EL_GORDO + 1 >= 99999) {
        numeroSiguiente_gordo = 0;
        comprobante = true;
    }
}

if (EL_SEGUNDO - 1 <= 0) {
    numeroAnterior_Segundo = 99999;
    comprobante = true;
} else {
    if (EL_SEGUNDO + 1 >= 99999) {
        numeroSiguiente_Segundo = 0;
        comprobante = true;
    }
}

//comprobacion de los numeros anteriores y siguiente 

if (!comprobante) {
    numeroAnterior_gordo = EL_GORDO - 1;
    numeroSiguiente_gordo = EL_GORDO + 1;

    numeroAnterior_Segundo = EL_SEGUNDO - 1;
    numeroSiguiente_Segundo = EL_SEGUNDO + 1;
}




//c)
comprobacionPedreas = false;
numeroPedrea;
pedreas.forEach((pedrea) => {
    misDecimos.forEach((miDecimo) => {
        if (miDecimo == pedrea) {
            comprobacionPedreas = true;
            numeroPedrea = miDecimo;
        }
    });
});


misDecimos.forEach((decimo) => {


    for (i = 0; i < array.length; i++) {
        if (decimo == EL_GORDO) {
            console.log("¡Premio Gordo! El número " + decimo + " gana 4.000.000€")
        } else {
            if (decimo == EL_SEGUNDO) {
                console.log("¡Segundo Premio! El número " + decimo + " gana 1.250.000€")
            } else {
                if (decimo == numeroAnterior_gordo || decimo == numeroSiguiente_gordo || decimo == numeroAnterior_Segundo || decimo == numeroSiguiente_Segundo) {
                    console.log("¡Aproximación! El número" + decimo + " gana 2.000€")
                } else {
                    if (comprobacionPedreas == true && decimo == numeroPedrea) {
                        console.log("¡Pedrea! El número " + numeroPedrea + " gana 1.000€")
                        //comprobacion extra para ver si hay mas de un decimo con pedrea
                    }
                }

            }
        }


    }
});


