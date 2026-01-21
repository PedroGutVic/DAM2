
//Debes hacer exportación por defecto
export function saludar(nombre) {
    return `Hola, ${nombre}`;
}


//Exportación normal
export function obtenerMomentoDelDia() {
    const hora = new Date().getHours();
    if (hora < 12) return "Buenos días";
    if (hora < 20) return "Buenas tardes";
    return "Buenas noches";
}
