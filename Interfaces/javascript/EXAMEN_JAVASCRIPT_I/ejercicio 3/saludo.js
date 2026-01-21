
//Debes hacer exportación por defecto
export default function saludar(nombre) {
    return `Hola, ${nombre}`;
}


//Exportación normal
export default function obtenerMomentoDelDia() {
    const hora = new Date().getHours();
    if (hora < 12) return "Buenos días";
    if (hora < 20) return "Buenas tardes";
    return "Buenas noches";
}
