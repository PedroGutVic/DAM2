import './footer.css';

const Footer = () => {
    return (
    <footer className="footer">
        <div className="footer-container">
        <p className="footer-text">&copy; {new Date().getFullYear()} MiSitioWeb. Todos los derechos reservados.</p>
        <div className="footer-links">
            <a href="#">Inicio</a>
            <a href="#">Sobre Nosotros</a>
            <a href="#">Contacto</a>
        </div>
        </div>
    </footer>
    );
}

export default Footer;