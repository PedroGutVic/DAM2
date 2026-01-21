const buttons = document.querySelectorAll('.button');
let turno = 0;

const comprobarGanador = () => {
    if (button1.textContent === 'o' && button2.textContent === 'o' && button3.textContent === 'o' ||
        button4.textContent === 'o' && button5.textContent === 'o' && button6.textContent === 'o' ||
        button7.textContent === 'o' && button8.textContent === 'o' && button9.textContent === 'o' ||
        button1.textContent === 'o' && button4.textContent === 'o' && button7.textContent === 'o' ||
        button2.textContent === 'o' && button5.textContent === 'o' && button8.textContent === 'o' ||
        button3.textContent === 'o' && button6.textContent === 'o' && button9.textContent === 'o' ||
        button1.textContent === 'o' && button5.textContent === 'o' && button9.textContent === 'o' ||
        button3.textContent === 'o' && button5.textContent === 'o' && button7.textContent === 'o') {
            alert("¡Gana el jugador 1!");
            return true;
        }
    if (button1.textContent === 'x' && button2.textContent === 'x' && button3.textContent === 'x' ||
        button4.textContent === 'x' && button5.textContent === 'x' && button6.textContent === 'x' ||
        button7.textContent === 'x' && button8.textContent === 'x' && button9.textContent === 'x' ||
        button1.textContent === 'x' && button4.textContent === 'x' && button7.textContent === 'x' ||
        button2.textContent === 'x' && button5.textContent === 'x' && button8.textContent === 'x' ||
        button3.textContent === 'x' && button6.textContent === 'x' && button9.textContent === 'x' ||
        button1.textContent === 'x' && button5.textContent === 'x' && button9.textContent === 'x' ||
        button3.textContent === 'x' && button5.textContent === 'x' && button7.textContent === 'x') {
            alert("¡Gana el jugador 2!");
            return true;
        }
    return false;
    }


buttons.forEach(button => {
    button.addEventListener('click', () => {
    
        if (button.textContent === 'o' || button.textContent === 'x') {
            return; // Si ya tiene una "o" o "x", no hacer nada
        }else{
            button.textContent = turno;
            turno+=1;
            console.log(turno);
        }
        
        if (turno % 2 == 0) {
            button.textContent = 'x';
               if (comprobarGanador()) {
            buttons.forEach(btn => btn.disabled = true); // Deshabilitar botones
            }
            return;
        }else{
            button.textContent = 'o';
            if (comprobarGanador()) {
            buttons.forEach(btn => btn.disabled = true); // Deshabilitar botones
        }
        }

        

    });
});
