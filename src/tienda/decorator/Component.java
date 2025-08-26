package tienda.decorator;

// Interfaz del patrón Decorator
// Trabaja sobre los precios para que los decoradores puedan actualizarlo
public interface Component {

    // Ejecuta la operación sobre el precio.
    void operacion(PrecioContexto ctx);
}
