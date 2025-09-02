package tienda.decorator;

// Trabaja sobre los precios para que los decoradores puedan actualizarlo
public interface Component {



    void operacion(PrecioContexto ctx);
}
